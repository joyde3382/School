using System;
using Personkartotek.DomainModel;
using Personkartotek.Infrastruture;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Personkartotek
{
    class Program
    {
        static void Main(string[] args)
        {
            CRUD myCRUD = new CRUD();

            Person p = new Person() { PID = 1 };

            Person Jens = new Person() { fullName = "Jens Jensen", personType = "Friend", Address = null };

            City randers = new City() { cityName = "Randers C", zipCode = 8900, countryRegion = "Denmark", addresses = new List<Address>() };

            Address JensAddress = new Address { streetAddress = "Randersvej 27b", cityAtAddress = randers, peopleAtAddress = new List<Person>() };

            //Jens.Address = JensAddress;

            randers.addresses = new List<Address>();
            randers.addresses.Add(JensAddress);

            JensAddress.peopleAtAddress = new List<Person>();
            JensAddress.peopleAtAddress.Add(Jens);

            

            myCRUD.AddPerson(ref Jens);
            myCRUD.AddCity(ref randers);
            myCRUD.AddAddress(ref JensAddress);


            Jens.Address = JensAddress;

            //Email JensEmail = new Email { email = "Jens123@gmail.com" };

            //ContactInfo JensPrivatePhone = new ContactInfo { phoneNumber = 11223344, phoneType = "Private", phoneCompany = "TDC" };

            //Note JensNote = new Note { note = "Jens is my dude" };

           
           

        }
    }
}
