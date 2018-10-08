namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class uniqueAddress : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Addresses",
                c => new
                    {
                        addressId = c.Int(nullable: false, identity: true),
                        streetAddress = c.String(maxLength: 128),
                        countryRegion = c.String(),
                        addressType_typeId = c.Int(),
                        cityAtAddress_cityId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.addressId)
                .ForeignKey("dbo.GenericTypes", t => t.addressType_typeId)
                .ForeignKey("dbo.Cities", t => t.cityAtAddress_cityId, cascadeDelete: true)
                .Index(t => t.addressId, unique: true)
                .Index(t => t.streetAddress, unique: true)
                .Index(t => t.addressType_typeId)
                .Index(t => t.cityAtAddress_cityId);
            
            CreateTable(
                "dbo.GenericTypes",
                c => new
                    {
                        typeId = c.Int(nullable: false, identity: true),
                        genericType = c.String(),
                    })
                .PrimaryKey(t => t.typeId)
                .Index(t => t.typeId, unique: true);
            
            CreateTable(
                "dbo.Cities",
                c => new
                    {
                        cityId = c.Int(nullable: false, identity: true),
                        cityName = c.String(),
                        zipCode = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.cityId)
                .Index(t => t.cityId, unique: true);
            
            CreateTable(
                "dbo.Person_Class",
                c => new
                    {
                        personId = c.Int(nullable: false, identity: true),
                        display_name = c.String(),
                        personType_personTypeId = c.Int(),
                    })
                .PrimaryKey(t => t.personId)
                .ForeignKey("dbo.PersonTypes", t => t.personType_personTypeId)
                .Index(t => t.personId, unique: true)
                .Index(t => t.personType_personTypeId);
            
            CreateTable(
                "dbo.ContactInfoes",
                c => new
                    {
                        contactId = c.Int(nullable: false, identity: true),
                        personId = c.Int(nullable: false),
                        phoneNumber = c.Int(nullable: false),
                        phoneCompany_typeId = c.Int(),
                        phoneType_typeId = c.Int(),
                    })
                .PrimaryKey(t => t.contactId)
                .ForeignKey("dbo.GenericTypes", t => t.phoneCompany_typeId)
                .ForeignKey("dbo.GenericTypes", t => t.phoneType_typeId)
                .ForeignKey("dbo.Person_Class", t => t.personId, cascadeDelete: true)
                .Index(t => t.contactId, unique: true)
                .Index(t => t.personId)
                .Index(t => t.phoneNumber, unique: true)
                .Index(t => t.phoneCompany_typeId)
                .Index(t => t.phoneType_typeId);
            
            CreateTable(
                "dbo.Emails",
                c => new
                    {
                        emailId = c.Int(nullable: false, identity: true),
                        personId = c.Int(nullable: false),
                        email = c.String(maxLength: 64),
                    })
                .PrimaryKey(t => t.emailId)
                .ForeignKey("dbo.Person_Class", t => t.personId, cascadeDelete: true)
                .Index(t => t.emailId, unique: true)
                .Index(t => t.personId)
                .Index(t => t.email, unique: true);
            
            CreateTable(
                "dbo.Notes",
                c => new
                    {
                        noteId = c.Int(nullable: false, identity: true),
                        personId = c.Int(nullable: false),
                        note = c.String(),
                    })
                .PrimaryKey(t => t.noteId)
                .ForeignKey("dbo.Person_Class", t => t.personId, cascadeDelete: true)
                .Index(t => t.noteId, unique: true)
                .Index(t => t.personId);
            
            CreateTable(
                "dbo.PersonTypes",
                c => new
                    {
                        personTypeId = c.Int(nullable: false, identity: true),
                        pType = c.String(),
                    })
                .PrimaryKey(t => t.personTypeId)
                .Index(t => t.personTypeId, unique: true);
            
            CreateTable(
                "dbo.Person_ClassAddress",
                c => new
                    {
                        Person_Class_personId = c.Int(nullable: false),
                        Address_addressId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.Person_Class_personId, t.Address_addressId })
                .ForeignKey("dbo.Person_Class", t => t.Person_Class_personId, cascadeDelete: true)
                .ForeignKey("dbo.Addresses", t => t.Address_addressId, cascadeDelete: true)
                .Index(t => t.Person_Class_personId)
                .Index(t => t.Address_addressId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Person_Class", "personType_personTypeId", "dbo.PersonTypes");
            DropForeignKey("dbo.Notes", "personId", "dbo.Person_Class");
            DropForeignKey("dbo.Emails", "personId", "dbo.Person_Class");
            DropForeignKey("dbo.ContactInfoes", "personId", "dbo.Person_Class");
            DropForeignKey("dbo.ContactInfoes", "phoneType_typeId", "dbo.GenericTypes");
            DropForeignKey("dbo.ContactInfoes", "phoneCompany_typeId", "dbo.GenericTypes");
            DropForeignKey("dbo.Person_ClassAddress", "Address_addressId", "dbo.Addresses");
            DropForeignKey("dbo.Person_ClassAddress", "Person_Class_personId", "dbo.Person_Class");
            DropForeignKey("dbo.Addresses", "cityAtAddress_cityId", "dbo.Cities");
            DropForeignKey("dbo.Addresses", "addressType_typeId", "dbo.GenericTypes");
            DropIndex("dbo.Person_ClassAddress", new[] { "Address_addressId" });
            DropIndex("dbo.Person_ClassAddress", new[] { "Person_Class_personId" });
            DropIndex("dbo.PersonTypes", new[] { "personTypeId" });
            DropIndex("dbo.Notes", new[] { "personId" });
            DropIndex("dbo.Notes", new[] { "noteId" });
            DropIndex("dbo.Emails", new[] { "email" });
            DropIndex("dbo.Emails", new[] { "personId" });
            DropIndex("dbo.Emails", new[] { "emailId" });
            DropIndex("dbo.ContactInfoes", new[] { "phoneType_typeId" });
            DropIndex("dbo.ContactInfoes", new[] { "phoneCompany_typeId" });
            DropIndex("dbo.ContactInfoes", new[] { "phoneNumber" });
            DropIndex("dbo.ContactInfoes", new[] { "personId" });
            DropIndex("dbo.ContactInfoes", new[] { "contactId" });
            DropIndex("dbo.Person_Class", new[] { "personType_personTypeId" });
            DropIndex("dbo.Person_Class", new[] { "personId" });
            DropIndex("dbo.Cities", new[] { "cityId" });
            DropIndex("dbo.GenericTypes", new[] { "typeId" });
            DropIndex("dbo.Addresses", new[] { "cityAtAddress_cityId" });
            DropIndex("dbo.Addresses", new[] { "addressType_typeId" });
            DropIndex("dbo.Addresses", new[] { "streetAddress" });
            DropIndex("dbo.Addresses", new[] { "addressId" });
            DropTable("dbo.Person_ClassAddress");
            DropTable("dbo.PersonTypes");
            DropTable("dbo.Notes");
            DropTable("dbo.Emails");
            DropTable("dbo.ContactInfoes");
            DropTable("dbo.Person_Class");
            DropTable("dbo.Cities");
            DropTable("dbo.GenericTypes");
            DropTable("dbo.Addresses");
        }
    }
}
