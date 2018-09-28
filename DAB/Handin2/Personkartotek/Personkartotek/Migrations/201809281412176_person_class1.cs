namespace Personkartotek.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class person_class1 : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.AlternativeContactInfoes",
                c => new
                    {
                        phoneNumber = c.Int(nullable: false, identity: true),
                        company = c.String(),
                    })
                .PrimaryKey(t => t.phoneNumber);
            
            CreateTable(
                "dbo.Person_class",
                c => new
                    {
                        display_name = c.String(nullable: false, maxLength: 128),
                        stateRegisterAddress = c.String(),
                        type = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.display_name);
            
            CreateTable(
                "dbo.PrimaryAddresses",
                c => new
                    {
                        addressFromPerson = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => t.addressFromPerson);
            
            CreateTable(
                "dbo.PrimaryContactInfoes",
                c => new
                    {
                        company = c.String(nullable: false, maxLength: 128),
                        phoneNumber = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.company);
            
        }
        
        public override void Down()
        {
            DropTable("dbo.PrimaryContactInfoes");
            DropTable("dbo.PrimaryAddresses");
            DropTable("dbo.Person_class");
            DropTable("dbo.AlternativeContactInfoes");
        }
    }
}
