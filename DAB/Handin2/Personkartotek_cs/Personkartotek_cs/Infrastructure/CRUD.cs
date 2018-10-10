using System;
using Personkartotek_cs.DomainModel;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace Personkartotek_cs.Infrastruture
{
    class CRUD
    {

        private Person currentPerson;
        public CRUD()
        {

            currentPerson = new Person() { personId = 0, fullName = "", Address = null, AlternativeAddress = null, ContactInfo = null, EmailAddress = null, personNote = null, personType = null };

        }

        private SqlConnection OpenConnection
        {

            get

            {

                //var con = new SqlConnection(@"Data Source=(localdb)\ProjectsV13;Initial Catalog=CraftManDB;Integrated Security=True");

                var con = new SqlConnection(@"Data Source=(localdb)\MSSQLLocalDB;Initial Catalog=Personkartotek_cs.DomainModel.PersonDBContext;Connect Timeout=30;Encrypt=False;TrustServerCertificate=False;ApplicationIntent=ReadWrite;MultiSubnetFailover=False");
                //var con = new SqlConnection(@"Data Source=(localdb)\MSSQLLocalDB;Initial Catalog=CraftManDB; User ID=Program;Password=Program123;Connect Timeout=30;Encrypt=False;TrustServerCertificate=False;ApplicationIntent=ReadWrite;MultiSubnetFailover=False");

                con.Open();

                return con;

            }

        }


        /**************** PERSON CRUD ****************/

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

                cmd.Parameters.AddWithValue("@Address_addressId", mPerson.Address.addressId);

                mPerson.personId = (int)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

        }

        public List<Person> GetPersons()
        {

            string sqlcmd = @"SELECT * FROM Person";

            using (var cmd = new SqlCommand(sqlcmd, OpenConnection))
            {

                SqlDataReader rdr = null;

                rdr = cmd.ExecuteReader();

                List<Person> PersonList = new List<Person>();

                Person mPerson = null;

                while (rdr.Read())
                {

                    mPerson = new Person();

                    mPerson.personId = (int)rdr["personId"];

                    mPerson.fullName = (string)rdr["fullName"];

                    mPerson.personType = (string)rdr["personType"];

                    PersonList.Add(mPerson);

                }

                return PersonList;

            }
        }

        public void UpdatePerson(ref Person mPerson)
        {

            // prepare command string

            string updateString =

               @"UPDATE Person

                        SET fullName= @fullName, personType = @personType, Address_addressId = @Address_addressId
                        WHERE personId = @personId";



            using (SqlCommand cmd = new SqlCommand(updateString, OpenConnection))

            {

                // Get your parameters ready 

                cmd.Parameters.AddWithValue("@fullName", mPerson.fullName);

                cmd.Parameters.AddWithValue("@personType", mPerson.personType);

                cmd.Parameters.AddWithValue("@Address_addressId", mPerson.Address.addressId);

                cmd.Parameters.AddWithValue("@personId", mPerson.personId);

                var id = (int)cmd.ExecuteNonQuery();

            }

        }

        public void DeletePerson(ref Person mPerson)
        {

            string deleteString = @"DELETE FROM Person WHERE (personId = @personId)";

            using (SqlCommand cmd = new SqlCommand(deleteString, OpenConnection))
            {

                cmd.Parameters.AddWithValue("@personId", mPerson.personId);

                var id = (int)cmd.ExecuteNonQuery();

                mPerson = null;

            }

        }

        /**************** Address CRUD ****************/
        public void AddAddress(ref Address mAddress)
        {

            // prepare command string using paramters in string and returning the given identity 



            string insertStringParam = @"INSERT INTO [Address] (streetAddress, cityAtAddress_cityId)

                                                    OUTPUT INSERTED.addressId  

                                                    VALUES (@streetAddress, @cityAtAddress_cityId)";



            using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))

            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@streetAddress", mAddress.streetAddress);

                cmd.Parameters.AddWithValue("@cityAtAddress_cityId", mAddress.cityAtAddress.cityId);

                mAddress.addressId = (int)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

        }

        public void UpdateAddress(ref Address mAddress)
        {

            // prepare command string

            string updateString =

               @"UPDATE Address

                        SET streetAddress = @streetAddress, cityAtAddress_cityId = @cityAtAddress_cityId
                        WHERE addressId = @addressId";



            using (SqlCommand cmd = new SqlCommand(updateString, OpenConnection))
            {

                // Get your parameters ready 

                cmd.Parameters.AddWithValue("@streetAddress", mAddress.streetAddress);

                cmd.Parameters.AddWithValue("@cityAtAddress_cityId", mAddress.cityAtAddress.cityId);

                cmd.Parameters.AddWithValue("@AddressId", mAddress.addressId);

                var id = (int)cmd.ExecuteNonQuery();

            }

        }

        public void DeleteAddress(ref Address mAddress)

        {

            string deleteString = @"DELETE FROM Address WHERE (addressId = @addressId)";

            using (SqlCommand cmd = new SqlCommand(deleteString, OpenConnection))
            {

                cmd.Parameters.AddWithValue("@addressId", mAddress.addressId);

                var id = (int)cmd.ExecuteNonQuery();

                mAddress = null;

            }

        }


        /**************** City CRUD ****************/


        public void AddCity(ref City mCity)
        {

            // prepare command string using paramters in string and returning the given identity 



            string insertStringParam = @"INSERT INTO [City] (cityName, zipCode, countryRegion)

                                                    OUTPUT INSERTED.cityId  

                                                    VALUES (@cityName, @zipCode, @countryRegion)";



            using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))

            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@cityName", mCity.cityName);

                cmd.Parameters.AddWithValue("@zipCode", mCity.zipCode);

                cmd.Parameters.AddWithValue("@countryRegion", mCity.countryRegion);

                mCity.cityId = (int)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

        }


        /**************** Email CRUD ****************/
        public void AddEmail(ref Email mEmail)
        {

            // prepare command string using paramters in string and returning the given identity 



            string insertStringParam = @"INSERT INTO [Email] (email, person_personId)

                                                    OUTPUT INSERTED.emailId

                                                    VALUES (@email, @person_personId)";



            using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))

            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@email", mEmail.email);//.ToString("yyyy-MM-dd HH:mm:ss"); 

                cmd.Parameters.AddWithValue("@person_personId", mEmail.person.personId);

                mEmail.emailId = (int)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

        }


        // FIX HERE
        public void UpdateEmail(ref Email mEmail)
        {

            // prepare command string

            string updateString =

               @"UPDATE Email

                        SET email = @email
                        WHERE emailId = @emailId";



            using (SqlCommand cmd = new SqlCommand(updateString, OpenConnection))
            {

                // Get your parameters ready 

                cmd.Parameters.AddWithValue("@email", mEmail.email);

                cmd.Parameters.AddWithValue("@emailId", mEmail.emailId);

                var id = (int)cmd.ExecuteNonQuery();

            }

        }
    }

    
}
