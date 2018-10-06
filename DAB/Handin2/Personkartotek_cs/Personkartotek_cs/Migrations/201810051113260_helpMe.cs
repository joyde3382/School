namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class helpMe : DbMigration
    {
        public override void Up()
        {
            RenameColumn(table: "dbo.ContactInfoes", name: "phonyType_typeId", newName: "phoneType_typeId");
            RenameIndex(table: "dbo.ContactInfoes", name: "IX_phonyType_typeId", newName: "IX_phoneType_typeId");
            CreateIndex("dbo.ContactInfoes", "phoneNumber", unique: true);
        }
        
        public override void Down()
        {
            DropIndex("dbo.ContactInfoes", new[] { "phoneNumber" });
            RenameIndex(table: "dbo.ContactInfoes", name: "IX_phoneType_typeId", newName: "IX_phonyType_typeId");
            RenameColumn(table: "dbo.ContactInfoes", name: "phoneType_typeId", newName: "phonyType_typeId");
        }
    }
}
