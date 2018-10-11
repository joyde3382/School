using System;
using Personkartotek_cs.DomainModel;
using Personkartotek_cs.Infrastruture;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;

namespace Personkartotek_cs
{
    class Program
    {
        static void Main(string[] args)
        {

            using (var db = new PersonDBContext())
            {
                CRUD newCRUD = new CRUD();

                if (!db.persons.Any())
                {
                    Program program = new Program();
                    program.StartDatabase(db);

                }

            }
        }
        public void StartDatabase(PersonDBContext db)
        {
            Console.WriteLine("Starting database");

            CRUD myCRUD = new CRUD();

            Person p = new Person() { personId = 1 };

            Address blankAddress = new Address() { addressId = 1 };

            Person Jens = new Person() { fullName = "Jens Jensen", personType = "Friend", Address = null, EmailAddress = new List<Email>(), ContactInfo = new List<ContactInfo>() };

            Person Otto = new Person() { fullName = "Otto Ottossen", personType = "Brother", Address = null };

            City randers = new City() { cityName = "Randers C", zipCode = 8900, countryRegion = "Denmark" };

            Address JensAddress = new Address { streetAddress = "Randersvej 27b", cityAtAddress = randers, personId = new List<Person>() };

            Address JensAAddress = new Address { streetAddress = "Aarhusvej 2", cityAtAddress = randers, personId = new List<Person>() };

            AlternativeAddress JensAlternativeAddress = new AlternativeAddress { attachedAddress = JensAAddress, attachedPerson = new List<Person>(), addressType = "Work" };

            Address OttosAddress = new Address { streetAddress = "Randersvej 10", cityAtAddress = randers, personId = new List<Person>() };

            Email JensEmail = new Email { email = "JensEmail@gmail.com", person = Jens };

            ContactInfo JensContactInfo = new ContactInfo { phoneNumber = 12345678, phoneCompany = "TDC", phoneType = "Private", person = Jens };

            /*********** ADD JENS TO DB ***********/

            Jens.Address = JensAddress;
            Jens.EmailAddress.Add(JensEmail);
            JensAddress.personId.Add(Jens);
            JensAAddress.personId.Add(Jens);

            JensAlternativeAddress.attachedPerson.Add(Jens);
            JensAlternativeAddress.attachedAddress = JensAAddress;
            Jens.AlternativeAddress.Add(JensAlternativeAddress);

            Jens.ContactInfo.Add(JensContactInfo);

            JensAddress.cityAtAddress = randers;
            JensAAddress.cityAtAddress = randers;


            // CRUD OPERATIONS ON JENS

            // Add operations
            myCRUD.AddCity(ref randers);
            myCRUD.AddAddress(ref JensAddress);
            myCRUD.AddAddress(ref JensAAddress);

            myCRUD.AddPerson(ref Jens);

            myCRUD.AddEmail(ref JensEmail);

            myCRUD.AddAlternativeAddress(ref JensAlternativeAddress);

            myCRUD.AddContactInfo(ref JensContactInfo);


            // Update operations
            myCRUD.UpdatePerson(ref Jens);

            JensAddress.streetAddress = "Randersvej 1";
            myCRUD.UpdateAddress(ref JensAddress);

            JensEmail.email = "NewEmailForJens@hotmail.com";
            myCRUD.UpdateEmail(ref JensEmail);

            JensContactInfo.phoneNumber = 55554444;
            myCRUD.UpdateContactInfo(ref JensContactInfo);

            JensAAddress.streetAddress = "NewAlternativeAddress 15";
            myCRUD.UpdateAddress(ref JensAAddress);
            JensAlternativeAddress.attachedAddress = JensAAddress;
            myCRUD.UpdateAlternativeAddress(ref JensAlternativeAddress);

            /*********** ADD OTTO TO DB ***********/
            OttosAddress.cityAtAddress = randers;
            Otto.Address = OttosAddress;
            OttosAddress.cityAtAddress = randers;
            myCRUD.AddAddress(ref OttosAddress);
            myCRUD.AddPerson(ref Otto);
            myCRUD.UpdatePerson(ref Otto);

            // Read operations
            var personList = myCRUD.GetPersons();

            int counter = 0;

            foreach (var person in personList)
            {

                Console.WriteLine($"\n {counter + 1}. person: \n");
                counter++;

                Console.WriteLine($"Name: {person.fullName} | Relation:  { person.personType } \n");
                Console.WriteLine("************************");

            }

            // Delete operations
            myCRUD.DeleteAlternativeAddress(ref JensAlternativeAddress);

            myCRUD.DeleteAddress(ref JensAAddress);
            myCRUD.DeleteAddress(ref JensAddress);
            myCRUD.DeleteAddress(ref OttosAddress);

            myCRUD.DeleteEmail(ref JensEmail);

            myCRUD.DeleteContactInfo(ref JensContactInfo);

            myCRUD.DeletePerson(ref Jens);
            myCRUD.DeletePerson(ref Otto);

            var personList2 = myCRUD.GetPersons();

            int counter2 = 0;

            foreach (var person in personList2)
            {

                Console.WriteLine($"\n {counter2 + 1}. person: \n");
                counter2++;

                Console.WriteLine($"Name: {person.fullName} | Relation:  { person.personType } \n");
                Console.WriteLine("************************");

            }
        }
    }
}
