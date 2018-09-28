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
                // Create and save a new Blog
                Console.Write("Enter a name for a new person: ");
                var name = Console.ReadLine();

                var primAddr = new PrimaryAddress {streetAddress = "scrubROad", countryRegion = "aarhus amt", zipCode = 8000, cityName = "aarhus" };
                db.primaryAddress.Add(primAddr);
                db.SaveChanges();

                var altAddr = new AlternativeAddress {streetAddress = "kkROad", countryRegion = "randers amt", zipCode = 8600, cityName = "randers" };
                db.alternativeAddress.Add(altAddr);
                db.SaveChanges();


                var my_email = new Email { email = "dsad@gmail.com"};
                db.emailAddress.Add(my_email);
                db.SaveChanges();

                var primConInf = new PrimaryContactInfo {phoneNumber = 11223344, company = "telenor"};
                db.primaryContactInfo.Add(primConInf);
                db.SaveChanges();




                var person = new Person_Class { fullName = name, type = "Hobo", StateRegisterAddress = "rrr" };
                db.persons.Add(person);
                db.primaryAddress.Add(primAddr);
                db.alternativeAddress.Add(altAddr);
                db.emailAddress.Add(my_email);
                db.primaryContactInfo.Add(primConInf);
                db.SaveChanges();


                // Display all Blogs from the database
                var query = from b in db.persons
                                orderby b.fullName
                                select b;

                Console.WriteLine("All persons in the database:");
                foreach (var item in query)
                {
                    Console.WriteLine(item.fullName);
                }
            }
        }
    }
}
