#include <Adafruit_NeoPixel.h>

#define NUM_LEDS 24
#define ROWS 4
#define LED_PER_ROW (unsigned char)(NUM_LEDS / ROWS)
#define DATA_PIN 8
#define BLINK_PERIOD 500

Adafruit_NeoPixel pixels(NUM_LEDS, DATA_PIN, NEO_GRB | NEO_KHZ800);

const uint32_t GREEN = pixels.Color(0, 255, 0);
const uint32_t RED = pixels.Color(255, 0, 0);
const uint32_t BLUE = pixels.Color(0, 0, 255);
const uint32_t BLACK = pixels.Color(0, 0, 0);
const uint32_t WHITE = pixels.Color(255, 255, 255);

const uint32_t metroColors[] = {pixels.Color(0, 0, 255), pixels.Color(255, 255, 0), pixels.Color(0, 255, 0), pixels.Color(255, 50, 0)};
const uint32_t busColors[] = {pixels.Color(255, 0, 255), pixels.Color(255, 255, 255), pixels.Color(255, 128, 0), pixels.Color(127, 127, 255)};

byte modes[ROWS]; // 0 = Not assigned     1 = Metro     2 = Traffic     3 = Bus
int animationFlags[ROWS];
long timer;
int demoPercentage = 100;

byte row = -1;
byte mode = -1;
byte option[3] = {0, 0, 0};
byte buffer[5];

void initRegisters()
{
    for (uint8_t i = 0; i < ROWS; i++)
    {
        modes[i] = 0;
        animationFlags[i] = 0;
    }
    timer = 0;
}

// Mirror every 2 rows when setting pixels to mimic the project's wiring
void setPixelColor(uint8_t pixelNumber, uint32_t color)
{
    if (((pixelNumber / LED_PER_ROW) % 2) == 1)
    {
        // The following expression makes absolutely no sense because I wrote it at 4 in the morning...
        // But hey... It works! :)
        pixelNumber = ((pixelNumber / LED_PER_ROW) * LED_PER_ROW) + (((pixelNumber / LED_PER_ROW) * LED_PER_ROW) + LED_PER_ROW) - pixelNumber - 1;
    }
    pixels.setPixelColor(pixelNumber, color);
}

// Set a row to black
void setRowOff()
{
    for (uint16_t i = row * LED_PER_ROW; i < (row + 1) * LED_PER_ROW; i++)
    {
        setPixelColor(i, BLACK);
    }
}

// Display the metro state
void setMetro()
{
    animationFlags[row] = option[0] << 1;
    for (uint8_t i = 0; i < 4; i++)
        setPixelColor((row * LED_PER_ROW) + i + 1, metroColors[i]);
}

// Blink animation when the metro is down
void updateMetro(uint8_t metroRow)
{
    for (uint8_t i = 0; i < LED_PER_ROW; i++)
    {
        if ((animationFlags[metroRow] & (1 << i)) && i > 0 && i < 5)
        {
            setPixelColor(i + (metroRow * LED_PER_ROW), ((animationFlags[metroRow] & (1 << (LED_PER_ROW + i))) > 0) ? BLACK : metroColors[i - 1]);
            animationFlags[metroRow] ^= (1 << (LED_PER_ROW + i));
        }
    }
}

// Display the traffic state (single color passed in the 3 optional bytes)
void setTraffic()
{
    uint32_t color = pixels.Color(option[0], option[1], option[2]);
    for (uint16_t i = row * LED_PER_ROW; i < (row + 1) * LED_PER_ROW; i++)
    {
        setPixelColor(i, color);
    }
}

// Display a countdown (progressbar)
void setBus()
{
    for (uint16_t i = row * LED_PER_ROW; i < (row + 1) * LED_PER_ROW; i++)
    {
        if (i < (row * LED_PER_ROW) + ((option[0] * LED_PER_ROW) / 100))
        {
            setPixelColor(i, busColors[row]);
        }
        else
        {
            setPixelColor(i, BLACK);
        }
    }
}

// Update all internal states (blinking, fading, sweeping...)
void updateStates()
{
    long timeStamp = millis();
    if (timeStamp - timer > BLINK_PERIOD)
    {
        timer = timeStamp;
        for (uint8_t i = 0; i < ROWS; i++)
        {
            if (modes[i] == 1)
            {
                updateMetro(i);
            }
        }
    }
    // Demo mode
    if (millis() % 1000 > 995)
    {
        --demoPercentage;
        if (demoPercentage <= 0)
        {
            demoPercentage = 100;
        }
        for (uint8_t i = 0; i < ROWS; i++)
        {
            if (modes[i] == 3)
            {
                row = i;
                mode = 3; 
                option[0] = demoPercentage;
                updateRow();
            }
        }
    }
}

void updateRow()
{
    modes[row] = mode;
    switch (mode)
    {
    case 0:
        // Not assigned
        setRowOff();
        break;
    case 1:
        // Métro
        setMetro();
        break;
    case 2:
        // Traffic
        setTraffic();
        break;
    case 3:
        // Bus
        setBus();
        break;
    default:
        // You fucked up boi
        setRowOff();
        break;
    }
}

void setup()
{
    Serial.begin(9600);
    pinMode(DATA_PIN, OUTPUT);
    pinMode(LED_BUILTIN, OUTPUT);
    pixels.setBrightness(100);
    pixels.begin();
    pixels.clear();
    pixels.show();
    initRegisters();

    // Initial setup with colors
    row = 0;
    mode = 1;
    option[0] = 0b0100;
    updateRow();

    row = 1;
    mode = 2;
    option[0] = 0xff;
    option[1] = 0x00;
    option[2] = 0x00;
    updateRow();

    row = 2;
    mode = 3;
    option[0] = 70;
    updateRow();

    row = 3;
    mode = 2;
    option[0] = 0x00;
    option[1] = 0xff;
    option[2] = 0x00;
    updateRow();
}

void loop()
{
    if (Serial.available() > 0)
    {
        size_t readBytes = Serial.readBytes(buffer, 5);

        if (readBytes == 5)
        {
            row = buffer[0];
            mode = buffer[1];
            option[0] = buffer[2];
            option[1] = buffer[3];
            option[2] = buffer[4];

            updateRow();
        }
    }

    // Update display
    updateStates();
    pixels.show();
}
