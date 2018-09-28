using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity;


namespace Personkartotek
{
    class PersonContext : DbContext
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
            modelBuilder.Entity<Person_class>()
                .Property(u => u.fullName)
                .HasColumnName("display_name");
        }


        public DbSet<Person_class> persons { get; set; }

        public DbSet<PrimaryAddress> primaryAddress { get; set; }

        //public DbSet<AlternativeAddress> alternativeAddress { get; set; }

        public DbSet<PrimaryContactInfo> primaryContactInfo { get; set; }

        public DbSet<AlternativeContactInfo> alternativeContactInfo { get; set; }

        //public DbSet<Email> emailAddress { get; set; }
    }
}
