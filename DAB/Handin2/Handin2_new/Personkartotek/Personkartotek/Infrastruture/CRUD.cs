using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Personkartotek.Infrastruture
{
    class CRUD
    {

        private 
        public CRUD()

        {

            currentPerson = new Personkartotek() { HID = 0, Fornavn = "", Efternavn = "", Ejer_Værktøjskasser = null, Fagområde = "", Ansættelsedato = new DateTime() };

        }
        public void AddHåndværkerDB(ref Håndværker hv)

        {

            // prepare command string using paramters in string and returning the given identity 



            string insertStringParam = @"INSERT INTO [Håndværker] (Ansættelsedato, Efternavn, Fagområde, Fornavn)

                                                    OUTPUT INSERTED.HåndværkerId  

                                                    VALUES (@Ansættelsedato, @Efternavn,@Fagområde,@Fornavn)";



            using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))

            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@Ansættelsedato", hv.Ansættelsedato.Date);//.ToString("yyyy-MM-dd HH:mm:ss"); ;

                cmd.Parameters.AddWithValue("@Efternavn", hv.Efternavn);

                cmd.Parameters.AddWithValue("@Fagområde", hv.Fagområde);

                cmd.Parameters.AddWithValue("@Fornavn", hv.Fornavn);

                hv.HID = (int)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

        }
    }
}
