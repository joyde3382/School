using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Personkartotek_cs
{
    class myCRUD
    {
        public void Create(PersonDBContext db)
        {

            PersonType mPersonType = new PersonType { pType = "Mother" };
            db.pType.Add(mPersonType);

            GenericType AddressType = new GenericType { genericType = "Home" };
            db.gType.Add(AddressType);

            GenericType PhoneCompany = new GenericType { genericType = "TDC" };
            db.gType.Add(PhoneCompany);

            GenericType PhoneType = new GenericType { genericType = "Private" };
            db.gType.Add(PhoneType);

            City CityName = new City { cityName = "Ålborg C", zipCode = 9000 };
            db.city.Add(CityName);

            Address Address = new Address { streetAddress = "Ålborgvej 27b", countryRegion = "Aarhus amt", addressType = AddressType, cityAtAddress = CityName };
            db.address.Add(Address);

            ContactInfo PrivatePhone = new ContactInfo { phoneNumber = 12345678, phoneType = PhoneType, phoneCompany = PhoneCompany };
            db.contactInfo.Add(PrivatePhone);

            Email Email = new Email { email = "Bodil555@gmail.com" };
            db.emailAddress.Add(Email);

            Note Note = new Note { note = "Bodil is a hurricane" };

            Person_Class Bodil = new Person_Class { fullName = "Bodil Storm", personType = mPersonType };

            Bodil.Address.Add(Address);
            Bodil.ContactInfo.Add(PrivatePhone);
            Bodil.EmailAddress.Add(Email);
            db.persons.Add(Bodil);
            db.SaveChanges();

        }

        public void Read(PersonDBContext db)
        {
            // Display all people from the database
            Console.WriteLine("All persons in the database:");
            var People = (from p in db.persons
                          select p);


            int counter = 0;
            
            foreach (var person in People)
            {
                int emailCount = 0;
                int addressCount = 0;
                int phoneCount = 0;

                Console.WriteLine($"\n {counter + 1}. person: \n");
                counter++;

                Console.WriteLine($"Name: {person.fullName} | Relation:  { person.personType.pType } \n");
                Console.WriteLine("************************");
                Console.WriteLine("Addresses:");

                foreach (var _address in person.Address)
                {
                    addressCount++;
                    Console.WriteLine($"Address {addressCount}:");
                    Console.WriteLine($"City: {_address.cityAtAddress.cityName} {_address.cityAtAddress.zipCode}");
                    Console.WriteLine($"Address: {_address.streetAddress}");
                    Console.WriteLine($"Region: {_address.countryRegion}");
                    Console.WriteLine($"AddressType: {_address.addressType.genericType}\n");
                }

                Console.WriteLine("\n************************");
                Console.WriteLine("ContactInfo:");

                foreach (var number in person.ContactInfo)
                {
                    phoneCount++;
                    Console.WriteLine($"Phone {phoneCount}:");
                    Console.WriteLine($"PhoneType: {number.phoneType.genericType}");
                    Console.WriteLine($"PhoneNumber: {number.phoneNumber}");
                    Console.WriteLine($"Company: {number.phoneCompany.genericType}\n");
                }


                Console.WriteLine("\n************************");
                Console.WriteLine("Emails:");
                
                foreach (var email in person.EmailAddress)
                {
                    emailCount++;
                    Console.WriteLine($"Email {emailCount}: {email.email}");
                    
                }
            }
        }

        public void Update(PersonDBContext db)
        {
            List<Person_Class> people = db.persons.ToList<Person_Class>();

            Person_Class mads = people.FirstOrDefault(m => m.fullName == "Bodil Storm");

            mads.fullName = "Bodil Lynild";

            db.SaveChanges();
        }



        public void Delete(PersonDBContext db) {
            // Find and delete Bodil 
            Person_Class bodil = db.persons.FirstOrDefault(p => p.fullName == "Bodil Lynild");

            db.persons.Remove(bodil);

            db.SaveChanges();
        }

    }
}
