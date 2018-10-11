namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class final : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Address",
                c => new
                    {
                        addressId = c.Int(nullable: false, identity: true),
                        streetAddress = c.String(),
                        cityAtAddress_cityId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.addressId)
                .ForeignKey("dbo.City", t => t.cityAtAddress_cityId, cascadeDelete: true)
                .Index(t => t.cityAtAddress_cityId);
            
            CreateTable(
                "dbo.City",
                c => new
                    {
                        cityId = c.Int(nullable: false, identity: true),
                        cityName = c.String(),
                        zipCode = c.Int(nullable: false),
                        countryRegion = c.String(),
                    })
                .PrimaryKey(t => t.cityId)
                .Index(t => t.cityId, unique: true);
            
            CreateTable(
                "dbo.Person",
                c => new
                    {
                        personId = c.Int(nullable: false, identity: true),
                        fullName = c.String(),
                        personType = c.String(),
                        addressId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.personId)
                .ForeignKey("dbo.Address", t => t.addressId, cascadeDelete: true)
                .Index(t => t.personId, unique: true)
                .Index(t => t.addressId);
            
            CreateTable(
                "dbo.AlternativeAddress",
                c => new
                    {
                        AAId = c.Int(nullable: false, identity: true),
                        addressType = c.String(),
                        attachedAddress_addressId = c.Int(),
                    })
                .PrimaryKey(t => t.AAId)
                .ForeignKey("dbo.Address", t => t.attachedAddress_addressId)
                .Index(t => t.attachedAddress_addressId);
            
            CreateTable(
                "dbo.ContactInfo",
                c => new
                    {
                        contactId = c.Int(nullable: false, identity: true),
                        phoneNumber = c.Int(nullable: false),
                        phoneType = c.String(),
                        phoneCompany = c.String(),
                        person_personId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.contactId)
                .ForeignKey("dbo.Person", t => t.person_personId, cascadeDelete: true)
                .Index(t => t.contactId, unique: true)
                .Index(t => t.phoneNumber, unique: true)
                .Index(t => t.person_personId);
            
            CreateTable(
                "dbo.Email",
                c => new
                    {
                        emailId = c.Int(nullable: false, identity: true),
                        email = c.String(maxLength: 64),
                        person_personId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.emailId)
                .ForeignKey("dbo.Person", t => t.person_personId, cascadeDelete: true)
                .Index(t => t.emailId, unique: true)
                .Index(t => t.email, unique: true)
                .Index(t => t.person_personId);
            
            CreateTable(
                "dbo.Note",
                c => new
                    {
                        noteId = c.Int(nullable: false, identity: true),
                        note = c.String(),
                        personId_personId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.noteId)
                .ForeignKey("dbo.Person", t => t.personId_personId, cascadeDelete: true)
                .Index(t => t.noteId, unique: true)
                .Index(t => t.personId_personId);
            
            CreateTable(
                "dbo.AlternativeAddressPersons",
                c => new
                    {
                        AlternativeAddress_AAId = c.Int(nullable: false),
                        Person_personId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.AlternativeAddress_AAId, t.Person_personId })
                .ForeignKey("dbo.AlternativeAddress", t => t.AlternativeAddress_AAId, cascadeDelete: true)
                .ForeignKey("dbo.Person", t => t.Person_personId, cascadeDelete: true)
                .Index(t => t.AlternativeAddress_AAId)
                .Index(t => t.Person_personId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Note", "personId_personId", "dbo.Person");
            DropForeignKey("dbo.Email", "person_personId", "dbo.Person");
            DropForeignKey("dbo.ContactInfo", "person_personId", "dbo.Person");
            DropForeignKey("dbo.AlternativeAddressPersons", "Person_personId", "dbo.Person");
            DropForeignKey("dbo.AlternativeAddressPersons", "AlternativeAddress_AAId", "dbo.AlternativeAddress");
            DropForeignKey("dbo.AlternativeAddress", "attachedAddress_addressId", "dbo.Address");
            DropForeignKey("dbo.Person", "addressId", "dbo.Address");
            DropForeignKey("dbo.Address", "cityAtAddress_cityId", "dbo.City");
            DropIndex("dbo.AlternativeAddressPersons", new[] { "Person_personId" });
            DropIndex("dbo.AlternativeAddressPersons", new[] { "AlternativeAddress_AAId" });
            DropIndex("dbo.Note", new[] { "personId_personId" });
            DropIndex("dbo.Note", new[] { "noteId" });
            DropIndex("dbo.Email", new[] { "person_personId" });
            DropIndex("dbo.Email", new[] { "email" });
            DropIndex("dbo.Email", new[] { "emailId" });
            DropIndex("dbo.ContactInfo", new[] { "person_personId" });
            DropIndex("dbo.ContactInfo", new[] { "phoneNumber" });
            DropIndex("dbo.ContactInfo", new[] { "contactId" });
            DropIndex("dbo.AlternativeAddress", new[] { "attachedAddress_addressId" });
            DropIndex("dbo.Person", new[] { "addressId" });
            DropIndex("dbo.Person", new[] { "personId" });
            DropIndex("dbo.City", new[] { "cityId" });
            DropIndex("dbo.Address", new[] { "cityAtAddress_cityId" });
            DropTable("dbo.AlternativeAddressPersons");
            DropTable("dbo.Note");
            DropTable("dbo.Email");
            DropTable("dbo.ContactInfo");
            DropTable("dbo.AlternativeAddress");
            DropTable("dbo.Person");
            DropTable("dbo.City");
            DropTable("dbo.Address");
        }
    }
}
