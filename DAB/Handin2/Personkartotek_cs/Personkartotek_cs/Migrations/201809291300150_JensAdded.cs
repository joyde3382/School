namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class JensAdded : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Addresses",
                c => new
                    {
                        addressId = c.Int(nullable: false, identity: true),
                        streetAddress = c.String(),
                        countryRegion = c.String(),
                        addressType_typeId = c.Int(),
                        cityAtAddress_cityId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.addressId)
                .ForeignKey("dbo.GenericTypes", t => t.addressType_typeId)
                .ForeignKey("dbo.Cities", t => t.cityAtAddress_cityId, cascadeDelete: true)
                .Index(t => t.addressType_typeId)
                .Index(t => t.cityAtAddress_cityId);
            
            CreateTable(
                "dbo.GenericTypes",
                c => new
                    {
                        typeId = c.Int(nullable: false, identity: true),
                        genericType = c.String(),
                    })
                .PrimaryKey(t => t.typeId);
            
            CreateTable(
                "dbo.Cities",
                c => new
                    {
                        cityId = c.Int(nullable: false, identity: true),
                        cityName = c.String(),
                        zipCode = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.cityId);
            
            CreateTable(
                "dbo.Person_Class",
                c => new
                    {
                        personId = c.Int(nullable: false, identity: true),
                        display_name = c.String(),
                        note_noteId = c.Int(),
                        personType_personTypeId = c.Int(),
                    })
                .PrimaryKey(t => t.personId)
                .ForeignKey("dbo.Notes", t => t.note_noteId)
                .ForeignKey("dbo.PersonTypes", t => t.personType_personTypeId)
                .Index(t => t.note_noteId)
                .Index(t => t.personType_personTypeId);
            
            CreateTable(
                "dbo.ContactInfoes",
                c => new
                    {
                        contactId = c.Int(nullable: false, identity: true),
                        phoneNumber = c.Int(nullable: false),
                        phoneCompany_typeId = c.Int(nullable: false),
                        phonyType_typeId = c.Int(),
                        Person_Class_personId = c.Int(),
                    })
                .PrimaryKey(t => t.contactId)
                .ForeignKey("dbo.GenericTypes", t => t.phoneCompany_typeId, cascadeDelete: true)
                .ForeignKey("dbo.GenericTypes", t => t.phonyType_typeId)
                .ForeignKey("dbo.Person_Class", t => t.Person_Class_personId)
                .Index(t => t.phoneCompany_typeId)
                .Index(t => t.phonyType_typeId)
                .Index(t => t.Person_Class_personId);
            
            CreateTable(
                "dbo.Emails",
                c => new
                    {
                        emailId = c.Int(nullable: false, identity: true),
                        email = c.String(nullable: false),
                        Person_Class_personId = c.Int(),
                    })
                .PrimaryKey(t => t.emailId)
                .ForeignKey("dbo.Person_Class", t => t.Person_Class_personId)
                .Index(t => t.Person_Class_personId);
            
            CreateTable(
                "dbo.Notes",
                c => new
                    {
                        noteId = c.Int(nullable: false, identity: true),
                        note = c.String(),
                    })
                .PrimaryKey(t => t.noteId);
            
            CreateTable(
                "dbo.PersonTypes",
                c => new
                    {
                        personTypeId = c.Int(nullable: false, identity: true),
                        type = c.String(),
                    })
                .PrimaryKey(t => t.personTypeId);
            
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
            DropForeignKey("dbo.Person_Class", "note_noteId", "dbo.Notes");
            DropForeignKey("dbo.Emails", "Person_Class_personId", "dbo.Person_Class");
            DropForeignKey("dbo.ContactInfoes", "Person_Class_personId", "dbo.Person_Class");
            DropForeignKey("dbo.ContactInfoes", "phonyType_typeId", "dbo.GenericTypes");
            DropForeignKey("dbo.ContactInfoes", "phoneCompany_typeId", "dbo.GenericTypes");
            DropForeignKey("dbo.Person_ClassAddress", "Address_addressId", "dbo.Addresses");
            DropForeignKey("dbo.Person_ClassAddress", "Person_Class_personId", "dbo.Person_Class");
            DropForeignKey("dbo.Addresses", "cityAtAddress_cityId", "dbo.Cities");
            DropForeignKey("dbo.Addresses", "addressType_typeId", "dbo.GenericTypes");
            DropIndex("dbo.Person_ClassAddress", new[] { "Address_addressId" });
            DropIndex("dbo.Person_ClassAddress", new[] { "Person_Class_personId" });
            DropIndex("dbo.Emails", new[] { "Person_Class_personId" });
            DropIndex("dbo.ContactInfoes", new[] { "Person_Class_personId" });
            DropIndex("dbo.ContactInfoes", new[] { "phonyType_typeId" });
            DropIndex("dbo.ContactInfoes", new[] { "phoneCompany_typeId" });
            DropIndex("dbo.Person_Class", new[] { "personType_personTypeId" });
            DropIndex("dbo.Person_Class", new[] { "note_noteId" });
            DropIndex("dbo.Addresses", new[] { "cityAtAddress_cityId" });
            DropIndex("dbo.Addresses", new[] { "addressType_typeId" });
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
