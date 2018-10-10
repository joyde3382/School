using System;
using Personkartotek.DomainModel;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace Personkartotek.Infrastruture
{
    class CRUD
    {

        private Person currentPerson;
        public CRUD()

        {

            currentPerson = new Person() {PID = 0,  fullName = "", Address = null, AlternativeAddress = null, ContactInfo = null, EmailAddress = null, personNote = null, personType = null};

        }

        private SqlConnection OpenConnection

        {

            get

            {

                //var con = new SqlConnection(@"Data Source=(localdb)\ProjectsV13;Initial Catalog=CraftManDB;Integrated Security=True");

                var con = new SqlConnection(@"Data Source=(localdb)\MSSQLLocalDB;Initial Catalog=Personkartotek;Connect Timeout=30;Encrypt=False;TrustServerCertificate=False;ApplicationIntent=ReadWrite;MultiSubnetFailover=False");
               //var con = new SqlConnection(@"Data Source=(localdb)\MSSQLLocalDB;Initial Catalog=CraftManDB; User ID=Program;Password=Program123;Connect Timeout=30;Encrypt=False;TrustServerCertificate=False;ApplicationIntent=ReadWrite;MultiSubnetFailover=False");

                con.Open();

                return con;

            }

        }
        public void AddPerson(ref Person mPerson)
        {

            // prepare command string using paramters in string and returning the given identity 



            string insertStringParam = @"INSERT INTO [Person] (fullName, personType)

                                                    OUTPUT INSERTED.personId  

                                                    VALUES (@fullName, @personType)";



            using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))

            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@fullName", mPerson.fullName);//.ToString("yyyy-MM-dd HH:mm:ss"); ;

                cmd.Parameters.AddWithValue("@personType", mPerson.personType);

                //cmd.Parameters.AddWithValue("@addressId", mPerson.Address.addressId);

                mPerson.PID = (long)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

        }
        public void AddAddress(ref Address mAddress)
        {

            // prepare command string using paramters in string and returning the given identity 



            string insertStringParam = @"INSERT INTO [Address] (streetAddress, cityAtAddress, peopleAtAddress)

                                                    OUTPUT INSERTED.addressId  

                                                    VALUES (@streetAddress, @cityAtAddress, @peopleAtAddress)";



            using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))

            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@streetAddress", mAddress.streetAddress);//.ToString("yyyy-MM-dd HH:mm:ss"); ;


                cmd.Parameters.AddWithValue("@cityAtAddress", mAddress.cityAtAddress);

                cmd.Parameters.AddWithValue("@peopleAtAddress", mAddress.peopleAtAddress);
                mAddress.addressId = (long)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

        }

        public void AddCity(ref City mCity)
        {

            // prepare command string using paramters in string and returning the given identity 



            string insertStringParam = @"INSERT INTO [City] (cityName, zipCode, countryRegion)

                                                    OUTPUT INSERTED.cityId  

                                                    VALUES (@cityName, @zipCode, @countryRegion)";



            using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))

            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@cityName", mCity.cityName);//.ToString("yyyy-MM-dd HH:mm:ss"); ;

                cmd.Parameters.AddWithValue("@zipCode", mCity.zipCode);

                cmd.Parameters.AddWithValue("@countryRegion", mCity.countryRegion);

                mCity.cityId = (long)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

        }
    }
}
