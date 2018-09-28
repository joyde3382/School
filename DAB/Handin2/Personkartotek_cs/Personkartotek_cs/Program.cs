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
                var mName = Console.ReadLine();

                Person_Class mPerson = new Person_Class {fullName = mName};
                db.persons.Add(mPerson);

                City mCity = new City { cityName = "Århus", zipCode = 8200 };
                db.city.Add(mCity);

                Address mAddress = new Address {streetAddress = "scrubROad", countryRegion = "aarhus amt" /*, city = mCity*/};
                db.address.Add(mAddress);
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
