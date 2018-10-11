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



            string insertStringParam = @"INSERT INTO [Person] (fullName, personType, addressId)

                                                    OUTPUT INSERTED.personId  

                                                    VALUES (@fullName, @personType, @addressId)";



            using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))
            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@fullName", mPerson.fullName);//.ToString("yyyy-MM-dd HH:mm:ss"); ;

                cmd.Parameters.AddWithValue("@personType", mPerson.personType);

                cmd.Parameters.AddWithValue("@addressId", mPerson.Address.addressId);

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

                        SET fullName= @fullName, personType = @personType
                        WHERE personId = @personId";



            using (SqlCommand cmd = new SqlCommand(updateString, OpenConnection))
            {

                // Get your parameters ready 

                cmd.Parameters.AddWithValue("@fullName", mPerson.fullName);

                cmd.Parameters.AddWithValue("@personType", mPerson.personType);

                //cmd.Parameters.AddWithValue("@addressId", mPerson.Address.addressId);

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

        /**************** Alternative Address CRUD ****************/
        public void AddAlternativeAddressPerson(ref AlternativeAddress mAAddress)
        {

            // prepare command string using paramters in string and returning the given identity 


            foreach (var a in mAAddress.attachedPerson)
            {

                string insertStringParam = @"INSERT INTO [AlternativeAddressPersons] (Person_personId, AlternativeAddress_AAId)

                                                    VALUES (@personId, @AAId)";



                using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))

                {

                    // Get your parameters ready                    

                    cmd.Parameters.AddWithValue("@personId", a.personId);

                    cmd.Parameters.AddWithValue("@AAId", mAAddress.AAId);

                    var id = (int)cmd.ExecuteNonQuery(); //Returns the identity of the new tuple/record

                }
            }

        }

        public void AddAlternativeAddress(ref AlternativeAddress mAAddress)
        {
            string sqlCmd = @"INSERT INTO [AlternativeAddress] (attachedAddress_addressId, addressType)
                            OUTPUT INSERTED.AAId
                            VALUES (@addressId, @addressType)";

            using (SqlCommand cmd = new SqlCommand(sqlCmd, OpenConnection))

            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@addressId", mAAddress.attachedAddress.addressId);

                cmd.Parameters.AddWithValue("@addressType", mAAddress.addressType);

                mAAddress.AAId = (int)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

            foreach (var a in mAAddress.attachedPerson)
            {

                string insertStringParam = @"INSERT INTO [AlternativeAddressPersons] (Person_personId, AlternativeAddress_AAId)

                                                    VALUES (@personId, @AAId)";

                using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))

                {

                    // Get your parameters ready                    

                    cmd.Parameters.AddWithValue("@personId", a.personId);

                    cmd.Parameters.AddWithValue("@AAId", mAAddress.AAId);

                    var id = (int)cmd.ExecuteNonQuery(); //Returns the identity of the new tuple/record

                }
            }
        }


        public void UpdateAlternativeAddress(ref AlternativeAddress mAAddress)
        {

            // prepare command string
            string updateString =

               @"UPDATE AlternativeAddress

                        SET attachedAddress_addressId = @addressId, addressType = @addressType
                        WHERE AAId = @AAId";

            using (SqlCommand cmd = new SqlCommand(updateString, OpenConnection))
            {

                // Get your parameters ready 

                cmd.Parameters.AddWithValue("@addressId", mAAddress.attachedAddress.addressId);

                cmd.Parameters.AddWithValue("@addressType", mAAddress.addressType);

                cmd.Parameters.AddWithValue("@AAId", mAAddress.AAId);

                var id = (int)cmd.ExecuteNonQuery();

            }

            foreach (var a in mAAddress.attachedPerson)
            {
                string updateString2 =

               @"UPDATE AlternativeAddressPersons

                        SET Person_personId = @personId
                        WHERE AlternativeAddress_AAId = @AAId";

                using (SqlCommand cmd = new SqlCommand(updateString2, OpenConnection))
                {

                    // Get your parameters ready 

                    cmd.Parameters.AddWithValue("@personId", a.personId);

                    cmd.Parameters.AddWithValue("@AAId", mAAddress.AAId);

                    var id = (int)cmd.ExecuteNonQuery();

                }
            }
        }

        public void DeleteAlternativeAddress(ref AlternativeAddress mAAddress)
        {

            string deleteString = @"DELETE FROM AlternativeAddress WHERE (AAId = @AAId)";

            using (SqlCommand cmd = new SqlCommand(deleteString, OpenConnection))
            {

                cmd.Parameters.AddWithValue("@AAId", mAAddress.AAId);

                var id = (int)cmd.ExecuteNonQuery();

            }

            mAAddress = null;

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
        public void DeleteEmail(ref Email mEmail)
        {

            string deleteString = @"DELETE FROM Email WHERE (emailId = @emailId)";

            using (SqlCommand cmd = new SqlCommand(deleteString, OpenConnection))
            {

                cmd.Parameters.AddWithValue("@emailId", mEmail.emailId);

                var id = (int)cmd.ExecuteNonQuery();

                mEmail = null;

            }

        }


        /**************** ContactInfo CRUD ****************/
        public void AddContactInfo(ref ContactInfo mContactInfo)
        {

            // prepare command string using paramters in string and returning the given identity 

            string insertStringParam = @"INSERT INTO [ContactInfo] (phoneNumber, phoneType, phoneCompany, person_personId)

                                                    OUTPUT INSERTED.contactId

                                                    VALUES (@phoneNumber, @phoneType, @phoneCompany, @person_personId)";

            using (SqlCommand cmd = new SqlCommand(insertStringParam, OpenConnection))
            {

                // Get your parameters ready                    

                cmd.Parameters.AddWithValue("@phoneNumber", mContactInfo.phoneNumber);//.ToString("yyyy-MM-dd HH:mm:ss"); 

                cmd.Parameters.AddWithValue("@phoneType", mContactInfo.phoneType);

                cmd.Parameters.AddWithValue("@phoneCompany", mContactInfo.phoneCompany);

                cmd.Parameters.AddWithValue("@person_personId", mContactInfo.person.personId);

                mContactInfo.contactId = (int)cmd.ExecuteScalar(); //Returns the identity of the new tuple/record

            }

        }

        public void UpdateContactInfo(ref ContactInfo mContactInfo)
        {
            // prepare command string

            string updateString =

               @"UPDATE ContactInfo

                        SET phoneNumber = @phoneNumber, phoneType = @phoneType, phoneCompany = @phoneCompany
                        WHERE contactId = @contactId";

            using (SqlCommand cmd = new SqlCommand(updateString, OpenConnection))
            {

                // Get your parameters ready 

                cmd.Parameters.AddWithValue("@phoneNumber", mContactInfo.phoneNumber);
                cmd.Parameters.AddWithValue("@phoneType", mContactInfo.phoneType);
                cmd.Parameters.AddWithValue("@phoneCompany", mContactInfo.phoneCompany);
                cmd.Parameters.AddWithValue("@contactId", mContactInfo.contactId);

                var id = (int)cmd.ExecuteNonQuery();

            }
        }
        public void DeleteContactInfo(ref ContactInfo mContactInfo)
        {
            string deleteString = @"DELETE FROM ContactInfo WHERE (contactId = @contactId)";

            using (SqlCommand cmd = new SqlCommand(deleteString, OpenConnection))
            {

                cmd.Parameters.AddWithValue("@contactId", mContactInfo.contactId);

                var id = (int)cmd.ExecuteNonQuery();

                mContactInfo = null;

            }

        }

    }

}
