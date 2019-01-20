#include <Adafruit_NeoPixel.h>

#define NUM_LEDS 24
#define ROWS 4
#define LED_PER_ROW NUM_LEDS / ROWS
#define DATA_PIN 8

Adafruit_NeoPixel pixels(NUM_LEDS, DATA_PIN, NEO_GRB | NEO_KHZ800);

const uint32_t GREEN = pixels.Color(0, 255, 0);
const uint32_t RED = pixels.Color(255, 0, 0);
const uint32_t BLUE = pixels.Color(0, 0, 255);
const uint32_t BLACK = pixels.Color(0, 0, 0);
const uint32_t WHITE = pixels.Color(255, 255, 255);

const uint32_t METRO_BLUE = pixels.Color(0, 0, 255);
const uint32_t METRO_YELLOW = pixels.Color(255, 255, 0);
const uint32_t METRO_GREEN = pixels.Color(0, 255, 0);
const uint32_t METRO_ORANGE = pixels.Color(255, 50, 0);

byte modes[ROWS]; // 0 = Not assigned     1 = Metro     2 = Traffic     3 = Bus

byte row = -1;
byte mode = -1;
byte option[3];

void setPixelColor(uint8_t pixelNumber, uint32_t color)
{
    if ((pixelNumber / LED_PER_ROW) % 2 == 1)
    {
        pixelNumber = ((((pixelNumber % LED_PER_ROW) + 1) * LED_PER_ROW) - 1) - (pixelNumber % LED_PER_ROW);
    }
    pixels.setPixelColor(pixelNumber, color);
}

void rowOn()
{
    for (uint16_t i = row * LED_PER_ROW; i < (row + 1) * LED_PER_ROW; i++)
    {
        setPixelColor(i, WHITE);
    }
    pixels.show();
}

void rowOff()
{
    for (uint16_t i = row * LED_PER_ROW; i < (row + 1) * LED_PER_ROW; i++)
    {
        setPixelColor(i, BLACK);
    }
    pixels.show();
}

void metro()
{
    setPixelColor((row * LED_PER_ROW) + 1, METRO_BLUE);
    setPixelColor((row * LED_PER_ROW) + 2, METRO_ORANGE);
    setPixelColor((row * LED_PER_ROW) + 3, METRO_GREEN);
    setPixelColor((row * LED_PER_ROW) + 4, METRO_YELLOW);
    pixels.show();
}

void traffic()
{
    uint32_t color = pixels.Color(option[0], option[1], option[2]);
    for (uint16_t i = row * LED_PER_ROW; i < (row + 1) * LED_PER_ROW; i++)
    {
        setPixelColor(i, color);
    }
    pixels.show();
}

void bus()
{
    for (uint16_t i = row * LED_PER_ROW; i < (row + 1) * LED_PER_ROW; i++)
    {

        if (i < (row * LED_PER_ROW) + ((option[0] * LED_PER_ROW) / 100))
        {
            setPixelColor(i, BLUE);
        }
        else 
        {
            setPixelColor(i, BLACK);
        }
    }
    pixels.show();
}

void updateRow()
{
    modes[row] = mode;
    switch (mode)
    {
    case 0:
        // Not assigned
        rowOff();
        break;
    case 1:
        // Métro
        metro();
        break;
    case 2:
        // Traffic
        traffic();
        break;
    case 3:
        // Bus
        bus();
        break;
    default:
        // You fucked up boi
        rowOff();
        break;
    }
}

void setup()
{
    Serial.begin(9600);
    pinMode(DATA_PIN, OUTPUT);
    pixels.setBrightness(100);
    pixels.begin();
}

void loop()
{
    // if (Serial.available() > 0)
    // {
    //     row = Serial.read();
    //     mode = Serial.read();
    //     option[0] = Serial.read();
    //     option[1] = Serial.read();
    //     option[2] = Serial.read();
    //     updateRow();
    // }
}
