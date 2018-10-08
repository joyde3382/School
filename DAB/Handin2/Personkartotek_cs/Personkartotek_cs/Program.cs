using System;
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
                myCRUD newCRUD = new myCRUD();

                if (!db.persons.Any())
                {
                    Program program = new Program();
                    program.StartDatabase(db);

                }

                Console.WriteLine("Default database");

                newCRUD.Read(db);

                Console.WriteLine("Press any key to create new person...");

                Console.ReadKey();

                Console.Clear();

                Console.WriteLine("Adding Bodil to database");

                Person_Class Bodil = new Person_Class();

                newCRUD.Create(db, Bodil);
                newCRUD.Read(db);

                Console.WriteLine("Press any key to give Bodil a new surname");
                Console.ReadKey();
                Console.Clear();



                newCRUD.UpdatePersonName(db, "Bodil Storm", "Dronning Bodil");

                newCRUD.UpdateEmail(db, "Bodil555@gmail.com", "BodilNewMail@gmail.com");

                newCRUD.UpdateContactInfo(db, 12345678, 99995555);
                newCRUD.UpdateNote(db, "Dronning Bodil", "Bodil just got a new note!");
                newCRUD.UpdateNote(db, "Dronning Bodil", "Bodil just got a second note!");


                newCRUD.Read(db);

                Console.WriteLine("Press any key to delete elements of Bodil");
                Console.ReadKey();
                Console.Clear();


                newCRUD.DeleteEmail(db, "BodilNewMail@gmail.com");
                newCRUD.DeleteAlternativeAddress(db, "Ålborgvej 27b");
                newCRUD.DeleteContactInfo(db, 99995555);
                newCRUD.DeleteNote(db, "Dronning Bodil");
                newCRUD.Read(db);

                Console.WriteLine("Press any key to delete Bodil");
                Console.ReadKey();
                Console.Clear();

                newCRUD.DeletePerson(db, "Dronning Bodil");
                newCRUD.Read(db);

            }
        }
        public void StartDatabase(PersonDBContext db)
        {
            City århus = new City { cityName = "Århus C", zipCode = 8200 };
            db.city.Add(århus);

            City randers = new City { cityName = "Randers C", zipCode = 8900 };
            db.city.Add(randers);

            PersonType Brother = new PersonType { pType = "Brother" };
            db.pType.Add(Brother);

            PersonType Friend = new PersonType { pType = "Friend" };
            db.pType.Add(Friend);

            GenericType Home = new GenericType { genericType = "Home" };
            db.gType.Add(Home);

            GenericType TDCCompany = new GenericType { genericType = "TDC" };
            db.gType.Add(TDCCompany);

            GenericType PrivatePhone = new GenericType { genericType = "Private" };
            db.gType.Add(PrivatePhone);

            GenericType Work = new GenericType { genericType = "Work" };
            db.gType.Add(Work);

            GenericType OisterCompany = new GenericType { genericType = "Oister" };
            db.gType.Add(OisterCompany);

            GenericType WorkPhone = new GenericType { genericType = "Work" };
            db.gType.Add(WorkPhone);

            // Add Person "Jens"
            Address JensAddress = new Address { streetAddress = "Randersvej 27b", countryRegion = "Århus amt", addressType = Home, cityAtAddress = århus };
            db.address.Add(JensAddress);

            ContactInfo JensPrivatePhone = new ContactInfo { phoneNumber = 11223344, phoneType = PrivatePhone, phoneCompany = TDCCompany };
            db.contactInfo.Add(JensPrivatePhone);

            Email JensEmail = new Email { email = "Jens123@gmail.com" };
            db.emailAddress.Add(JensEmail);

            Note JensNote = new Note { note = "Jens is my dude" };

            Person_Class Jens = new Person_Class { fullName = "Jens Petersen", personType = Brother };

            Jens.Address.Add(JensAddress);
            Jens.ContactInfo.Add(JensPrivatePhone);
            Jens.EmailAddress.Add(JensEmail);
            //Jens.note
            db.persons.Add(Jens);

            // Add Person "Karl"
            Address KarlHomeAddress = new Address { streetAddress = "Sunset Boulevard 33", countryRegion = "Østjylland", addressType = Home, cityAtAddress = randers };
            db.address.Add(KarlHomeAddress);

            Address KarlWorkAddress = new Address { streetAddress = "Østervold 23", countryRegion = "Østjylland", addressType = Work, cityAtAddress = randers };
            db.address.Add(KarlWorkAddress);

            ContactInfo KarlWorkPhone = new ContactInfo { phoneNumber = 55667788, phoneType = WorkPhone, phoneCompany = OisterCompany };
            db.contactInfo.Add(KarlWorkPhone);

            ContactInfo KarlPrivatePhone = new ContactInfo { phoneNumber = 22222222, phoneType = PrivatePhone, phoneCompany = TDCCompany };
            db.contactInfo.Add(KarlPrivatePhone);

            Email KarlEmail = new Email { email = "Karl321@gmail.com" };
            db.emailAddress.Add(KarlEmail);

            Person_Class Karl = new Person_Class { fullName = "Karl Sørensen", personType = Friend };


            Karl.Address.Add(KarlHomeAddress);
            Karl.Address.Add(KarlWorkAddress);
            Karl.ContactInfo.Add(KarlWorkPhone);
            Karl.ContactInfo.Add(KarlPrivatePhone);
            Karl.EmailAddress.Add(KarlEmail);
            db.persons.Add(Karl);

            // Add Person "Hans"
            Address HansWorkAddress = new Address { streetAddress = "Sunset Boulevard 33", countryRegion = "Østjylland", addressType = Work, cityAtAddress = randers };
            db.address.Add(HansWorkAddress);

            ContactInfo HansWorkPhone = new ContactInfo { phoneNumber = 222233333, phoneType = WorkPhone, phoneCompany = OisterCompany };
            db.contactInfo.Add(KarlWorkPhone);

            Email HansEmail = new Email { email = "Hans555@gmail.com" };
            db.emailAddress.Add(KarlEmail);

            Person_Class Hans = new Person_Class { fullName = "Hans Andersen", personType = Friend };

            Hans.Address.Add(HansWorkAddress);
            Hans.ContactInfo.Add(HansWorkPhone);
            Hans.EmailAddress.Add(HansEmail);
            db.persons.Add(Hans);

            db.SaveChanges();
        }
    }
}
