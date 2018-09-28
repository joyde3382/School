using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;


namespace Personkartotek_cs
{
    class PersonDBContext : DbContext
    {



        //protected override DbEntityValidationResult ValidateEntity(DbEntityEntry entityEntry, IDictionary<object, object> items)

        //{

        //    var validationResult = new DbEntityValidationResult(entityEntry, new List<DbValidationError>());

        //    var entity = entityEntry.Entity as Person;

        //    Person person = entity;

        //    //if (person?.Adresser.Count == 0)

        //    //{

        //    //    validationResult.ValidationErrors.Add(new DbValidationError("Adresser", "You need at least one address!"));

        //    //}



        //    return validationResult;

        //}


        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Person_Class>()
                .Property(u => u.fullName)
                .HasColumnName("display_name");
        }


        public DbSet<Person_Class> persons { get; set; }

        public DbSet<Address> address { get; set; }

        public DbSet<ContactInfo> contactInfo { get; set; }
    
        public DbSet<genericType> gType { get; set; }

        public DbSet<PersonType> pType { get; set; }

        public DbSet<City> city { get; set; }

        public DbSet<Email> emailAddress { get; set; }
    }
}
