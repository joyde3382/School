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

                Console.WriteLine("Default database");

                CRUD myCRUD = new CRUD();

                Person p = new Person() { personId = 1 };

                Address blankAddress = new Address() { addressId = 1 };

                Person Jens = new Person() { fullName = "Jens Jensen", personType = "Friend", Address = null, EmailAddress = new List<Email>() };

                Person Otto = new Person() { fullName = "Otto Ottossen", personType = "Brother", Address = null };

                City randers = new City() { cityName = "Randers C", zipCode = 8900, countryRegion = "Denmark"};

                Address JensAddress = new Address { streetAddress = "Randersvej 27b", cityAtAddress = randers, personId = new List<Person>() };

                Address JensAAddress = new Address { streetAddress = "Aarhusvej 2", cityAtAddress = randers, personId = new List<Person>() };

                AlternativeAddress JensAlternativeAddress = new AlternativeAddress { attachedAddress = JensAAddress, attachedPerson = new List<Person>(), addressType = "Work"};

                Address OttosAddress = new Address { streetAddress = "Randersvej 10", cityAtAddress = randers, personId = new List<Person>() };

                Email JensEmail = new Email { email = "JensEmail@gmail.com" , person = Jens };

                Jens.Address = JensAddress;
                Jens.EmailAddress.Add(JensEmail);
                JensAddress.personId.Add(Jens);
                JensAAddress.personId.Add(Jens);

                JensAlternativeAddress.attachedPerson.Add(Jens);
                JensAlternativeAddress.attachedAddress = JensAAddress;
                Jens.AlternativeAddress.Add(JensAlternativeAddress);

                //Otto.Address = OttosAddress;

                JensAddress.cityAtAddress = randers;
                JensAAddress.cityAtAddress = randers;
                //OttosAddress.cityAtAddress = randers;

                myCRUD.AddCity(ref randers);
                myCRUD.AddAddress(ref JensAddress);
                //myCRUD.AddAddress(ref OttosAddress);
                myCRUD.AddAddress(ref JensAAddress);
                
                myCRUD.AddPerson(ref Jens);
                //myCRUD.AddPerson(ref Otto);

                myCRUD.AddEmail(ref JensEmail);

                myCRUD.AddAlternativeAddressPerson(ref JensAlternativeAddress);
                myCRUD.AddAlternativeAddress(ref JensAlternativeAddress); /// HERE TEST

                myCRUD.UpdatePerson(ref Jens);
                myCRUD.UpdatePerson(ref Otto);

                JensAddress.streetAddress = "Randersvej 1";

                myCRUD.UpdateAddress(ref JensAddress);

                //myCRUD.DeletePerson(ref Jens);

                var personList =  myCRUD.GetPersons();

                //var People = (from p in db.persons
                //              select p);


                int counter = 0;

                foreach (var person in personList)
                {
                    int emailCount = 0;
                    int addressCount = 0;
                    int phoneCount = 0;
                    int noteCount = 0;

                    Console.WriteLine($"\n {counter + 1}. person: \n");
                    counter++;

                    Console.WriteLine($"Name: {person.fullName} | Relation:  { person.personType } \n");
                    Console.WriteLine("************************");

                }

                JensEmail.email = "NewEmailForJens@hotmail.com";

                myCRUD.DeleteAddress(ref JensAddress);

                myCRUD.UpdateEmail(ref JensEmail);


                //newCRUD.Read(db);

                //Console.WriteLine("Press any key to create new person...");

                //Console.ReadKey();

                //Console.Clear();

                //Console.WriteLine("Adding Bodil to database");

                //Person Bodil = new Person();

                //newCRUD.Create(db, Bodil);
                //newCRUD.Read(db);

                //Console.WriteLine("Press any key to give Bodil a new surname");
                //Console.ReadKey();
                //Console.Clear();



                //newCRUD.UpdatePersonName(db, "Bodil Storm", "Dronning Bodil");

                //newCRUD.UpdateEmail(db, "Bodil555@gmail.com", "BodilNewMail@gmail.com");

                //newCRUD.UpdateContactInfo(db, 12345678, 99995555);
                //newCRUD.UpdateNote(db, "Dronning Bodil", "Bodil just got a new note!");
                //newCRUD.UpdateNote(db, "Dronning Bodil", "Bodil just got a second note!");


                //newCRUD.Read(db);

                //Console.WriteLine("Press any key to delete elements of Bodil");
                //Console.ReadKey();
                //Console.Clear();


                //newCRUD.DeleteEmail(db, "BodilNewMail@gmail.com");
                //newCRUD.DeleteAlternativeAddress(db, "Ålborgvej 27b");
                //newCRUD.DeleteContactInfo(db, 99995555);
                //newCRUD.DeleteNote(db, "Dronning Bodil");
                //newCRUD.Read(db);

                //Console.WriteLine("Press any key to delete Bodil");
                //Console.ReadKey();
                //Console.Clear();

                //newCRUD.DeletePerson(db, "Dronning Bodil");
                //newCRUD.Read(db);

            }
        }
        public void StartDatabase(PersonDBContext db)
        {


            //City århus = new City { cityName = "Århus C", zipCode = 8200 };
            //db.city.Add(århus);

            //City randers = new City { cityName = "Randers C", zipCode = 8900 };
            //db.city.Add(randers);

            //PersonType Brother = new PersonType { pType = "Brother" };
            //db.pType.Add(Brother);

            //PersonType Friend = new PersonType { pType = "Friend" };
            //db.pType.Add(Friend);

            //GenericType Home = new GenericType { genericType = "Home" };
            //db.gType.Add(Home);

            //GenericType TDCCompany = new GenericType { genericType = "TDC" };
            //db.gType.Add(TDCCompany);

            //GenericType PrivatePhone = new GenericType { genericType = "Private" };
            //db.gType.Add(PrivatePhone);

            //GenericType Work = new GenericType { genericType = "Work" };
            //db.gType.Add(Work);

            //GenericType OisterCompany = new GenericType { genericType = "Oister" };
            //db.gType.Add(OisterCompany);

            //GenericType WorkPhone = new GenericType { genericType = "Work" };
            //db.gType.Add(WorkPhone);

            //// Add Person "Jens"
            //Address JensAddress = new Address { streetAddress = "Randersvej 27b", countryRegion = "Århus amt", addressType = Home, cityAtAddress = århus };
            //db.address.Add(JensAddress);

            //ContactInfo JensPrivatePhone = new ContactInfo { phoneNumber = 11223344, phoneType = PrivatePhone, phoneCompany = TDCCompany };
            //db.contactInfo.Add(JensPrivatePhone);

            //Email JensEmail = new Email { email = "Jens123@gmail.com" };
            //db.emailAddress.Add(JensEmail);

            //Note JensNote = new Note { note = "Jens is my dude" };

            //Person Jens = new Person { fullName = "Jens Petersen", personType = Brother };

            //Jens.Address.Add(JensAddress);
            //Jens.ContactInfo.Add(JensPrivatePhone);
            //Jens.EmailAddress.Add(JensEmail);
            ////Jens.note
            //db.persons.Add(Jens);

            //// Add Person "Karl"
            //Address KarlHomeAddress = new Address { streetAddress = "Sunset Boulevard 33", countryRegion = "Østjylland", addressType = Home, cityAtAddress = randers };
            //db.address.Add(KarlHomeAddress);

            //Address KarlWorkAddress = new Address { streetAddress = "Østervold 23", countryRegion = "Østjylland", addressType = Work, cityAtAddress = randers };
            //db.address.Add(KarlWorkAddress);

            //ContactInfo KarlWorkPhone = new ContactInfo { phoneNumber = 55667788, phoneType = WorkPhone, phoneCompany = OisterCompany };
            //db.contactInfo.Add(KarlWorkPhone);

            //ContactInfo KarlPrivatePhone = new ContactInfo { phoneNumber = 22222222, phoneType = PrivatePhone, phoneCompany = TDCCompany };
            //db.contactInfo.Add(KarlPrivatePhone);

            //Email KarlEmail = new Email { email = "Karl321@gmail.com" };
            //db.emailAddress.Add(KarlEmail);

            //Person Karl = new Person { fullName = "Karl Sørensen", personType = Friend };


            //Karl.Address.Add(KarlHomeAddress);
            //Karl.Address.Add(KarlWorkAddress);
            //Karl.ContactInfo.Add(KarlWorkPhone);
            //Karl.ContactInfo.Add(KarlPrivatePhone);
            //Karl.EmailAddress.Add(KarlEmail);
            //db.persons.Add(Karl);

            //// Add Person "Hans"
            //Address HansWorkAddress = new Address { streetAddress = "Sunset Boulevard 33", countryRegion = "Østjylland", addressType = Work, cityAtAddress = randers };
            //db.address.Add(HansWorkAddress);

            //ContactInfo HansWorkPhone = new ContactInfo { phoneNumber = 222233333, phoneType = WorkPhone, phoneCompany = OisterCompany };
            //db.contactInfo.Add(KarlWorkPhone);

            //Email HansEmail = new Email { email = "Hans555@gmail.com" };
            //db.emailAddress.Add(KarlEmail);

            //Person Hans = new Person { fullName = "Hans Andersen", personType = Friend };

            //Hans.Address.Add(HansWorkAddress);
            //Hans.ContactInfo.Add(HansWorkPhone);
            //Hans.EmailAddress.Add(HansEmail);
            //db.persons.Add(Hans);

            //db.SaveChanges();

        
        }
    }
}
