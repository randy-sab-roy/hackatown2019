using System;
using System.Collections.Generic;
namespace scripts
{
    class Program
    {
        static Dictionary<int,HashSet<int>> Routes = new Dictionary<int, HashSet<int>>();
        static void Main(string[] args)
        {
            // Read the file and display it line by line.  
            int counter = 0;  
            string line;
            System.IO.StreamReader file =   new System.IO.StreamReader(@"C:\Users\Thierry Dubois\Downloads\gtfs_stm\scripts\Trips.txt");  
            while((line = file.ReadLine()) != null)  
            {  
                line = line.Replace("\"", "");
                if(counter != 0)
                {
                    string[] arret = line.Split(",");
                    int id = int.Parse(arret[0]) * 10  + int.Parse(arret[3]);
                    if(Routes.ContainsKey((int)id))
                    {
                        int tripNo = int.Parse(arret[2]);
                        if(!Routes[id].Contains(tripNo))
                        {
                            Routes[id].Add(tripNo);
                        }
                    }
                    else
                    {
                        HashSet<int> liste = new HashSet<int>();
                        Routes.Add(id, liste);
                    }
                    
                }
                counter++;  
            }  
            
            file.Close(); 
            System.IO.StreamReader file2 =   new System.IO.StreamReader(@"C:\Users\Thierry Dubois\Downloads\gtfs_stm\scripts\Stop_times.txt");
            int counter2 = 0;
            var total = new System.Text.StringBuilder();
            while((line = file2.ReadLine()) != null)  
            {  
                string[] trip = new string[6];
                if(counter2 == 0)
                {
                    System.IO.File.AppendAllText(@"C:\Users\Thierry Dubois\Downloads\gtfs_stm\scripts\newTimes.txt", line);
                }
                if(counter2 != 0)
                {
                    line = line.Replace("\"", "");
                    trip = line.Split(",");
                    int tripID;
                    try{
                        tripID = int.Parse(trip[1]);
                    }catch(Exception e){
                        continue;
                    }
                    foreach (var route in Routes) 
                    { 
                        if(route.Value.Contains(tripID))
                        {
                            trip[1] = route.Key.ToString();
                        }
                    }
                }
                //join trip into string
                String newline = trip[0]+ ","  + trip[1] + ",\"" + trip[2] + "\",\"" + trip[3] + "\"," + trip[4] + "," + trip[5] + "\n";
                //append line in file
                total.Append(newline);
                counter2++;  
            }  
            System.IO.File.AppendAllText(@"C:\Users\Thierry Dubois\Downloads\gtfs_stm\scripts\newTimes.txt", total.ToString());
            file2.Close();
        }
    }
}
