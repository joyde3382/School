namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Person1 : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.AlternativeAddresses",
                c => new
                    {
                        addressType = c.String(nullable: false, maxLength: 128),
                        streetAddress = c.String(),
                        countryRegion = c.String(),
                        zipCode = c.Int(nullable: false),
                        cityName = c.String(),
                        attachedPerson_StateRegisterAddress = c.String(maxLength: 128),
                        attachedAddress_addressType = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.addressType)
                .ForeignKey("dbo.Person_Class", t => t.attachedPerson_StateRegisterAddress)
                .ForeignKey("dbo.PrimaryAddresses", t => t.attachedAddress_addressType)
                .Index(t => t.attachedPerson_StateRegisterAddress)
                .Index(t => t.attachedAddress_addressType);
            
            CreateTable(
                "dbo.PrimaryAddresses",
                c => new
                    {
                        addressType = c.String(nullable: false, maxLength: 128),
                        streetAddress = c.String(),
                        countryRegion = c.String(),
                        zipCode = c.Int(nullable: false),
                        cityName = c.String(),
                    })
                .PrimaryKey(t => t.addressType);
            
            CreateTable(
                "dbo.Person_Class",
                c => new
                    {
                        StateRegisterAddress = c.String(nullable: false, maxLength: 128),
                        display_name = c.String(),
                        type = c.String(),
                        note_noteId = c.Int(),
                        PrimaryAddress_addressType = c.String(maxLength: 128),
                        PrimaryContactInfo_phoneNumber = c.Int(),
                        PrimaryEmailAddress_email = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.StateRegisterAddress)
                .ForeignKey("dbo.Notes", t => t.note_noteId)
                .ForeignKey("dbo.PrimaryAddresses", t => t.PrimaryAddress_addressType)
                .ForeignKey("dbo.PrimaryContactInfoes", t => t.PrimaryContactInfo_phoneNumber)
                .ForeignKey("dbo.Emails", t => t.PrimaryEmailAddress_email)
                .Index(t => t.note_noteId)
                .Index(t => t.PrimaryAddress_addressType)
                .Index(t => t.PrimaryContactInfo_phoneNumber)
                .Index(t => t.PrimaryEmailAddress_email);
            
            CreateTable(
                "dbo.AlternativeContactInfoes",
                c => new
                    {
                        phoneNumber = c.Int(nullable: false, identity: true),
                        company = c.Int(nullable: false),
                        Person_Class_StateRegisterAddress = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.phoneNumber)
                .ForeignKey("dbo.Person_Class", t => t.Person_Class_StateRegisterAddress)
                .Index(t => t.Person_Class_StateRegisterAddress);
            
            CreateTable(
                "dbo.Notes",
                c => new
                    {
                        noteId = c.Int(nullable: false, identity: true),
                    })
                .PrimaryKey(t => t.noteId);
            
            CreateTable(
                "dbo.PrimaryContactInfoes",
                c => new
                    {
                        phoneNumber = c.Int(nullable: false, identity: true),
                        company = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.phoneNumber);
            
            CreateTable(
                "dbo.Emails",
                c => new
                    {
                        email = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => t.email);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.AlternativeAddresses", "attachedAddress_addressType", "dbo.PrimaryAddresses");
            DropForeignKey("dbo.Person_Class", "PrimaryEmailAddress_email", "dbo.Emails");
            DropForeignKey("dbo.Person_Class", "PrimaryContactInfo_phoneNumber", "dbo.PrimaryContactInfoes");
            DropForeignKey("dbo.Person_Class", "PrimaryAddress_addressType", "dbo.PrimaryAddresses");
            DropForeignKey("dbo.Person_Class", "note_noteId", "dbo.Notes");
            DropForeignKey("dbo.AlternativeContactInfoes", "Person_Class_StateRegisterAddress", "dbo.Person_Class");
            DropForeignKey("dbo.AlternativeAddresses", "attachedPerson_StateRegisterAddress", "dbo.Person_Class");
            DropIndex("dbo.AlternativeContactInfoes", new[] { "Person_Class_StateRegisterAddress" });
            DropIndex("dbo.Person_Class", new[] { "PrimaryEmailAddress_email" });
            DropIndex("dbo.Person_Class", new[] { "PrimaryContactInfo_phoneNumber" });
            DropIndex("dbo.Person_Class", new[] { "PrimaryAddress_addressType" });
            DropIndex("dbo.Person_Class", new[] { "note_noteId" });
            DropIndex("dbo.AlternativeAddresses", new[] { "attachedAddress_addressType" });
            DropIndex("dbo.AlternativeAddresses", new[] { "attachedPerson_StateRegisterAddress" });
            DropTable("dbo.Emails");
            DropTable("dbo.PrimaryContactInfoes");
            DropTable("dbo.Notes");
            DropTable("dbo.AlternativeContactInfoes");
            DropTable("dbo.Person_Class");
            DropTable("dbo.PrimaryAddresses");
            DropTable("dbo.AlternativeAddresses");
        }
    }
}
