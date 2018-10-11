namespace Personkartotek_cs.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class fromLaptop1 : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.AlternativeAddressPersons", "AlternativeAddress_AAId", "dbo.AlternativeAddress");
            DropIndex("dbo.AlternativeAddress", new[] { "AAId" });
            DropIndex("dbo.AlternativeAddressPersons", new[] { "AlternativeAddress_AAId" });
            DropPrimaryKey("dbo.AlternativeAddress");
            DropPrimaryKey("dbo.AlternativeAddressPersons");
            AlterColumn("dbo.AlternativeAddress", "AAId", c => c.Int(nullable: false, identity: true));
            AlterColumn("dbo.AlternativeAddressPersons", "AlternativeAddress_AAId", c => c.Int(nullable: false));
            AddPrimaryKey("dbo.AlternativeAddress", "AAId");
            AddPrimaryKey("dbo.AlternativeAddressPersons", new[] { "AlternativeAddress_AAId", "Person_personId" });
            CreateIndex("dbo.AlternativeAddress", "AAId", unique: true);
            CreateIndex("dbo.AlternativeAddressPersons", "AlternativeAddress_AAId");
            AddForeignKey("dbo.AlternativeAddressPersons", "AlternativeAddress_AAId", "dbo.AlternativeAddress", "AAId", cascadeDelete: true);
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.AlternativeAddressPersons", "AlternativeAddress_AAId", "dbo.AlternativeAddress");
            DropIndex("dbo.AlternativeAddressPersons", new[] { "AlternativeAddress_AAId" });
            DropIndex("dbo.AlternativeAddress", new[] { "AAId" });
            DropPrimaryKey("dbo.AlternativeAddressPersons");
            DropPrimaryKey("dbo.AlternativeAddress");
            AlterColumn("dbo.AlternativeAddressPersons", "AlternativeAddress_AAId", c => c.Long(nullable: false));
            AlterColumn("dbo.AlternativeAddress", "AAId", c => c.Long(nullable: false, identity: true));
            AddPrimaryKey("dbo.AlternativeAddressPersons", new[] { "AlternativeAddress_AAId", "Person_personId" });
            AddPrimaryKey("dbo.AlternativeAddress", "AAId");
            CreateIndex("dbo.AlternativeAddressPersons", "AlternativeAddress_AAId");
            CreateIndex("dbo.AlternativeAddress", "AAId", unique: true);
            AddForeignKey("dbo.AlternativeAddressPersons", "AlternativeAddress_AAId", "dbo.AlternativeAddress", "AAId", cascadeDelete: true);
        }
    }
}
