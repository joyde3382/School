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

                if (!db.persons.Any())
                {
                    Program program = new Program();
                    program.StartDatabase(db);
                }

                // Display all people from the database
                Console.WriteLine("All persons in the database:");
                var People = from b in db.persons select b;

             
                int counter = 0;

                foreach (var person in People)
                {


                    Console.WriteLine($"\n {counter + 1}. person: \n");
                    counter++;

                    Console.WriteLine($"Name: {person.fullName} | Relation:  { person.personType.pType } \n");
                    Console.WriteLine("************************");
                    Console.WriteLine("Addresses:");

                    foreach (var _address in person.Address)
                    {
                        Console.WriteLine($"City: {_address.cityAtAddress.cityName} {_address.cityAtAddress.zipCode}");
                        Console.WriteLine($"Address: {_address.streetAddress}");
                        Console.WriteLine($"Region: {_address.countryRegion}");
                        Console.WriteLine($"AddressType: {_address.addressType.genericType}");
                    }

                    Console.WriteLine("\n************************");
                    Console.WriteLine("ContactInfo:");

                    foreach (var number in person.ContactInfo)
                    {
                        Console.WriteLine($"PhoneType: {number.phonyType.genericType}");
                        Console.WriteLine($"PhoneNumber: {number.phoneNumber}");
                        Console.WriteLine($"Company: {number.phoneCompany.genericType}");
                    }
                }

                Console.WriteLine("\n");

            }
        }
        public void StartDatabase(PersonDBContext db)
        {
            City århus = new City { cityName = "Århus", zipCode = 8200 };
            db.city.Add(århus);

            City randers = new City { cityName = "Randers", zipCode = 8900 };
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

            ContactInfo JensPrivatePhone = new ContactInfo { phoneNumber = 11223344, phonyType = PrivatePhone, phoneCompany = TDCCompany };
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
            Address KarlAddress = new Address { streetAddress = "Sunset Boulevard 33", countryRegion = "Østjylland", addressType = Work, cityAtAddress = randers };
            db.address.Add(JensAddress);

            ContactInfo KarlWorkPhone = new ContactInfo { phoneNumber = 55667788, phonyType = WorkPhone, phoneCompany = OisterCompany };
            db.contactInfo.Add(KarlWorkPhone);

            ContactInfo KarlPrivatePhone = new ContactInfo { phoneNumber = 22222222, phonyType = PrivatePhone, phoneCompany = TDCCompany };
            db.contactInfo.Add(KarlPrivatePhone);

            Email KarlEmail = new Email { email = "Karl321@gmail.com" };
            db.emailAddress.Add(KarlEmail);

            Person_Class Karl = new Person_Class { fullName = "Karl Sørensen", personType = Friend };


            Karl.Address.Add(KarlAddress);
            Karl.ContactInfo.Add(KarlWorkPhone);
            Karl.ContactInfo.Add(KarlPrivatePhone);
            Karl.EmailAddress.Add(KarlEmail);
            db.persons.Add(Karl);
            db.SaveChanges();
        }
    }
}
