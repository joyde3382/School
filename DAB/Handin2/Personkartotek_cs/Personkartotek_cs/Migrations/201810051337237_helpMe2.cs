namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class helpMe2 : DbMigration
    {
        public override void Up()
        {
            AlterColumn("dbo.Emails", "email", c => c.String(nullable: false, maxLength: 64));
            CreateIndex("dbo.GenericTypes", "typeId", unique: true);
            CreateIndex("dbo.Cities", "cityId", unique: true);
            CreateIndex("dbo.Person_Class", "personId", unique: true);
            CreateIndex("dbo.ContactInfoes", "contactId", unique: true);
            CreateIndex("dbo.Emails", "emailId", unique: true);
            CreateIndex("dbo.Emails", "email", unique: true);
            CreateIndex("dbo.Notes", "noteId", unique: true);
            CreateIndex("dbo.PersonTypes", "personTypeId", unique: true);
        }
        
        public override void Down()
        {
            DropIndex("dbo.PersonTypes", new[] { "personTypeId" });
            DropIndex("dbo.Notes", new[] { "noteId" });
            DropIndex("dbo.Emails", new[] { "email" });
            DropIndex("dbo.Emails", new[] { "emailId" });
            DropIndex("dbo.ContactInfoes", new[] { "contactId" });
            DropIndex("dbo.Person_Class", new[] { "personId" });
            DropIndex("dbo.Cities", new[] { "cityId" });
            DropIndex("dbo.GenericTypes", new[] { "typeId" });
            AlterColumn("dbo.Emails", "email", c => c.String(nullable: false));
        }
    }
}
