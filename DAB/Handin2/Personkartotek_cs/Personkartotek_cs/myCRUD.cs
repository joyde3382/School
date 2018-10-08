using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Personkartotek_cs
{
    class myCRUD
    {
        public void Create(PersonDBContext db, Person_Class person)
        {

            // Get data from database

            City Aarhus = db.city.FirstOrDefault(b => b.cityName == "Århus C");

            PersonType Friend = db.pType.FirstOrDefault(v => v.pType == "Friend");

            GenericType Work = db.gType.FirstOrDefault(t => t.genericType == "Work");

            GenericType Home = db.gType.FirstOrDefault(t => t.genericType == "Home");

            GenericType TDC = db.gType.FirstOrDefault(t => t.genericType == "TDC");

            GenericType Private = db.gType.FirstOrDefault(t => t.genericType == "Private");

            Address PrimaryAddress = new Address { streetAddress = "Ålborgvej 27b", countryRegion = "Aarhus amt", addressType = Home, cityAtAddress = Aarhus };
            db.address.Add(PrimaryAddress);

            Address AlternativeAddress = new Address { streetAddress = "Ålborgvej 10", countryRegion = "Aarhus amt", addressType = Work, cityAtAddress = Aarhus };
            db.address.Add(AlternativeAddress);

            ContactInfo PrivatePhone = new ContactInfo { phoneNumber = 12345678, phoneType = Private, phoneCompany = TDC };
            db.contactInfo.Add(PrivatePhone);

            Email Email = new Email { email = "Bodil555@gmail.com" };
            db.emailAddress.Add(Email);

            Note Note = new Note { note = "Bodil is a hurricane" };
            db.note.Add(Note);

            person = new Person_Class { fullName = "Bodil Storm", personType = Friend };

            person.Address.Add(PrimaryAddress);
            person.Address.Add(AlternativeAddress);
            person.ContactInfo.Add(PrivatePhone);
            person.EmailAddress.Add(Email);
            person.personNote.Add(Note);

            db.persons.Add(person);
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
                int noteCount = 0;

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

                Console.WriteLine("\n************************");
                Console.WriteLine("Notes:");

                if (person.personNote.Count > 0)
                {

                    foreach (var note in person.personNote)
                    {
                        noteCount++;
                        Console.WriteLine($"Note {noteCount}: {note.note}");

                    }
                }
            }
        }

        public void UpdatePersonName(PersonDBContext db, string personName, string personNewName)
        {
            List<Person_Class> people = db.persons.ToList<Person_Class>();

            Person_Class Person = people.FirstOrDefault(m => m.fullName == personName);

            Person.fullName = personNewName;

            db.SaveChanges();
        }

        public void UpdateEmail(PersonDBContext db, string currentEmail, string newEmail)
        {
            List<Email> emails = db.emailAddress.ToList<Email>();

            Email email = emails.FirstOrDefault(m => m.email == currentEmail);

            email.email = newEmail;

            db.SaveChanges();
        }

        public void UpdateAlternativeAddress(PersonDBContext db, string currentAddress, string newAddress)
        {
            List<Address> alternativeAddress = db.address.ToList<Address>();

            Address tempAddress = alternativeAddress.FirstOrDefault(m => m.streetAddress == currentAddress);

            tempAddress.streetAddress = newAddress;

            db.SaveChanges();
        } 

        public void UpdatePrimaryAddress(PersonDBContext db, string currentAddress, string newAddress)
        {
            List<Address> primaryAddress = db.address.ToList<Address>();

            Address tempAddress = primaryAddress.FirstOrDefault(m => m.streetAddress == currentAddress);

            tempAddress.streetAddress = newAddress;

            db.SaveChanges();
        }

        public void UpdateContactInfo(PersonDBContext db, int currentNumber, int newNumber)
        {
            List<ContactInfo> ContactInfo = db.contactInfo.ToList<ContactInfo>();

            ContactInfo newPhoneNumber = ContactInfo.FirstOrDefault(m => m.phoneNumber == currentNumber);

            newPhoneNumber.phoneNumber = newNumber;

            db.SaveChanges();
        }

        public void UpdateNote(PersonDBContext db, string personName, string newNote )
        {
            List<Note> Note = db.note.ToList<Note>();

            List<Person_Class> person = db.persons.ToList<Person_Class>();

            Person_Class personsNote = person.FirstOrDefault(m => m.fullName == personName);

            Note NewNote = Note.FirstOrDefault(m => m.personId == personsNote.personId);

            NewNote.note = newNote;

            db.SaveChanges();
        }


        public void DeletePerson(PersonDBContext db, string personName)
        {
            // Find and delete Bodil 
            Person_Class person = db.persons.FirstOrDefault(p => p.fullName == personName);

            db.persons.Remove(person);

            db.SaveChanges();
        }

        public void DeleteAlternativeAddress(PersonDBContext db, string address)
        {
            // Find and delete address 
            Address tempAddress = db.address.FirstOrDefault(p => p.streetAddress == address);

            db.address.Remove(tempAddress);

            db.SaveChanges();
        }

        public void DeleteEmail(PersonDBContext db, string emailAddress)
        {
            // Find and delete Bodil 
            Email tempEmail = db.emailAddress.FirstOrDefault(p => p.email == emailAddress);

            db.emailAddress.Remove(tempEmail);

            db.SaveChanges();
        }

        public void DeleteNote(PersonDBContext db, string personName)
        {

            List<Person_Class> person = db.persons.ToList<Person_Class>();
            List<Note> Note = db.note.ToList<Note>();

            Person_Class personsNote = person.FirstOrDefault(m => m.fullName == personName);

            Note oldNote = Note.FirstOrDefault(m => m.personId == personsNote.personId);

            db.note.Remove(oldNote);

            db.SaveChanges();
        }

        public void DeleteContactInfo(PersonDBContext db, int number)
        {
            // Find and delete Bodil 
            ContactInfo tempContactInfo = db.contactInfo.FirstOrDefault(p => p.phoneNumber == number);

            db.contactInfo.Remove(tempContactInfo);

            db.SaveChanges();
        }
    }
}
