using System;
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
            using (var db = new PersonContext())
            {
                // Create and save a new Blog
                Console.Write("Enter a name for a new person: ");
                var name = Console.ReadLine();

                var person = new Person_class { fullName = name, type = 1, stateRegisterAddress = "sda"};
                db.persons.Add(person);
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
