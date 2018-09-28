namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class addedKeys : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.AlternativeAddresses", "attachedPerson_fullName", "dbo.Person_Class");
            DropForeignKey("dbo.AlternativeContactInfoes", "Person_Class_fullName", "dbo.Person_Class");
            DropForeignKey("dbo.Person_Class", "PrimaryAddress_addressType", "dbo.PrimaryAddresses");
            DropForeignKey("dbo.Person_Class", "PrimaryContactInfo_phoneNumber", "dbo.PrimaryContactInfoes");
            DropForeignKey("dbo.Person_Class", "PrimaryEmailAddress_email", "dbo.Emails");
            DropForeignKey("dbo.AlternativeAddresses", "attachedAddress_addressType", "dbo.PrimaryAddresses");
            DropIndex("dbo.AlternativeAddresses", new[] { "attachedPerson_fullName" });
            DropIndex("dbo.AlternativeAddresses", new[] { "attachedAddress_addressType" });
            DropIndex("dbo.Person_Class", new[] { "PrimaryAddress_addressType" });
            DropIndex("dbo.Person_Class", new[] { "PrimaryContactInfo_phoneNumber" });
            DropIndex("dbo.Person_Class", new[] { "PrimaryEmailAddress_email" });
            DropIndex("dbo.AlternativeContactInfoes", new[] { "Person_Class_fullName" });
            DropPrimaryKey("dbo.Person_Class");
            DropPrimaryKey("dbo.Emails");
            CreateTable(
                "dbo.Addresses",
                c => new
                    {
                        addressId = c.Int(nullable: false, identity: true),
                        streetAddress = c.String(),
                        countryRegion = c.String(),
                        addressType_typeId = c.Int(),
                        Person_Class_personID = c.Int(),
                    })
                .PrimaryKey(t => t.addressId)
                .ForeignKey("dbo.genericTypes", t => t.addressType_typeId)
                .ForeignKey("dbo.Person_Class", t => t.Person_Class_personID)
                .Index(t => t.addressType_typeId)
                .Index(t => t.Person_Class_personID);
            
            CreateTable(
                "dbo.genericTypes",
                c => new
                    {
                        typeId = c.Int(nullable: false, identity: true),
                        type = c.String(),
                    })
                .PrimaryKey(t => t.typeId);
            
            CreateTable(
                "dbo.Cities",
                c => new
                    {
                        cityId = c.Int(nullable: false, identity: true),
                        cityName = c.String(),
                        zipCode = c.Int(nullable: false),
                        Address_addressId = c.Int(),
                    })
                .PrimaryKey(t => t.cityId)
                .ForeignKey("dbo.Addresses", t => t.Address_addressId)
                .Index(t => t.Address_addressId);
            
            CreateTable(
                "dbo.ContactInfoes",
                c => new
                    {
                        contactId = c.Int(nullable: false, identity: true),
                        phoneNumber = c.Int(nullable: false),
                        company_typeId = c.Int(),
                        Person_Class_personID = c.Int(),
                    })
                .PrimaryKey(t => t.contactId)
                .ForeignKey("dbo.genericTypes", t => t.company_typeId)
                .ForeignKey("dbo.Person_Class", t => t.Person_Class_personID)
                .Index(t => t.company_typeId)
                .Index(t => t.Person_Class_personID);
            
            CreateTable(
                "dbo.PersonTypes",
                c => new
                    {
                        personTypeId = c.Int(nullable: false, identity: true),
                        type = c.String(),
                    })
                .PrimaryKey(t => t.personTypeId);
            
            AddColumn("dbo.Person_Class", "personID", c => c.Int(nullable: false, identity: true));
            AddColumn("dbo.Person_Class", "type_personTypeId", c => c.Int());
            AddColumn("dbo.Notes", "note", c => c.String());
            AddColumn("dbo.Emails", "emailId", c => c.Int(nullable: false, identity: true));
            AddColumn("dbo.Emails", "Person_Class_personID", c => c.Int());
            AlterColumn("dbo.Person_Class", "display_name", c => c.String());
            AlterColumn("dbo.Emails", "email", c => c.String());
            AddPrimaryKey("dbo.Person_Class", "personID");
            AddPrimaryKey("dbo.Emails", "emailId");
            CreateIndex("dbo.Emails", "Person_Class_personID");
            CreateIndex("dbo.Person_Class", "type_personTypeId");
            AddForeignKey("dbo.Emails", "Person_Class_personID", "dbo.Person_Class", "personID");
            AddForeignKey("dbo.Person_Class", "type_personTypeId", "dbo.PersonTypes", "personTypeId");
            DropColumn("dbo.Person_Class", "StateRegisterAddress");
            DropColumn("dbo.Person_Class", "type");
            DropColumn("dbo.Person_Class", "PrimaryAddress_addressType");
            DropColumn("dbo.Person_Class", "PrimaryContactInfo_phoneNumber");
            DropColumn("dbo.Person_Class", "PrimaryEmailAddress_email");
            DropTable("dbo.AlternativeAddresses");
            DropTable("dbo.PrimaryAddresses");
            DropTable("dbo.AlternativeContactInfoes");
            DropTable("dbo.PrimaryContactInfoes");
        }
        
        public override void Down()
        {
            CreateTable(
                "dbo.PrimaryContactInfoes",
                c => new
                    {
                        phoneNumber = c.Int(nullable: false, identity: true),
                        company = c.String(),
                    })
                .PrimaryKey(t => t.phoneNumber);
            
            CreateTable(
                "dbo.AlternativeContactInfoes",
                c => new
                    {
                        phoneNumber = c.Int(nullable: false, identity: true),
                        company = c.Int(nullable: false),
                        Person_Class_fullName = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.phoneNumber);
            
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
                "dbo.AlternativeAddresses",
                c => new
                    {
                        addressType = c.String(nullable: false, maxLength: 128),
                        streetAddress = c.String(),
                        countryRegion = c.String(),
                        zipCode = c.Int(nullable: false),
                        cityName = c.String(),
                        attachedPerson_fullName = c.String(maxLength: 128),
                        attachedAddress_addressType = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.addressType);
            
            AddColumn("dbo.Person_Class", "PrimaryEmailAddress_email", c => c.String(maxLength: 128));
            AddColumn("dbo.Person_Class", "PrimaryContactInfo_phoneNumber", c => c.Int());
            AddColumn("dbo.Person_Class", "PrimaryAddress_addressType", c => c.String(maxLength: 128));
            AddColumn("dbo.Person_Class", "type", c => c.String());
            AddColumn("dbo.Person_Class", "StateRegisterAddress", c => c.String());
            DropForeignKey("dbo.Person_Class", "type_personTypeId", "dbo.PersonTypes");
            DropForeignKey("dbo.Emails", "Person_Class_personID", "dbo.Person_Class");
            DropForeignKey("dbo.ContactInfoes", "Person_Class_personID", "dbo.Person_Class");
            DropForeignKey("dbo.Addresses", "Person_Class_personID", "dbo.Person_Class");
            DropForeignKey("dbo.ContactInfoes", "company_typeId", "dbo.genericTypes");
            DropForeignKey("dbo.Cities", "Address_addressId", "dbo.Addresses");
            DropForeignKey("dbo.Addresses", "addressType_typeId", "dbo.genericTypes");
            DropIndex("dbo.Person_Class", new[] { "type_personTypeId" });
            DropIndex("dbo.Emails", new[] { "Person_Class_personID" });
            DropIndex("dbo.ContactInfoes", new[] { "Person_Class_personID" });
            DropIndex("dbo.ContactInfoes", new[] { "company_typeId" });
            DropIndex("dbo.Cities", new[] { "Address_addressId" });
            DropIndex("dbo.Addresses", new[] { "Person_Class_personID" });
            DropIndex("dbo.Addresses", new[] { "addressType_typeId" });
            DropPrimaryKey("dbo.Emails");
            DropPrimaryKey("dbo.Person_Class");
            AlterColumn("dbo.Emails", "email", c => c.String(nullable: false, maxLength: 128));
            AlterColumn("dbo.Person_Class", "display_name", c => c.String(nullable: false, maxLength: 128));
            DropColumn("dbo.Emails", "Person_Class_personID");
            DropColumn("dbo.Emails", "emailId");
            DropColumn("dbo.Notes", "note");
            DropColumn("dbo.Person_Class", "type_personTypeId");
            DropColumn("dbo.Person_Class", "personID");
            DropTable("dbo.PersonTypes");
            DropTable("dbo.ContactInfoes");
            DropTable("dbo.Cities");
            DropTable("dbo.genericTypes");
            DropTable("dbo.Addresses");
            AddPrimaryKey("dbo.Emails", "email");
            AddPrimaryKey("dbo.Person_Class", "display_name");
            CreateIndex("dbo.AlternativeContactInfoes", "Person_Class_fullName");
            CreateIndex("dbo.Person_Class", "PrimaryEmailAddress_email");
            CreateIndex("dbo.Person_Class", "PrimaryContactInfo_phoneNumber");
            CreateIndex("dbo.Person_Class", "PrimaryAddress_addressType");
            CreateIndex("dbo.AlternativeAddresses", "attachedAddress_addressType");
            CreateIndex("dbo.AlternativeAddresses", "attachedPerson_fullName");
            AddForeignKey("dbo.AlternativeAddresses", "attachedAddress_addressType", "dbo.PrimaryAddresses", "addressType");
            AddForeignKey("dbo.Person_Class", "PrimaryEmailAddress_email", "dbo.Emails", "email");
            AddForeignKey("dbo.Person_Class", "PrimaryContactInfo_phoneNumber", "dbo.PrimaryContactInfoes", "phoneNumber");
            AddForeignKey("dbo.Person_Class", "PrimaryAddress_addressType", "dbo.PrimaryAddresses", "addressType");
            AddForeignKey("dbo.AlternativeContactInfoes", "Person_Class_fullName", "dbo.Person_Class", "display_name");
            AddForeignKey("dbo.AlternativeAddresses", "attachedPerson_fullName", "dbo.Person_Class", "display_name");
        }
    }
}
