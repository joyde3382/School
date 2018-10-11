CREATE TABLE [dbo].[Person] (
    [personId]   INT            IDENTITY (1, 1) NOT NULL,
    [fullName]   NVARCHAR (MAX) NULL,
    [personType] NVARCHAR (MAX) NULL,
    [addressId]  INT            NOT NULL,
    CONSTRAINT [PK_dbo.Person] PRIMARY KEY CLUSTERED ([personId] ASC),
    CONSTRAINT [FK_dbo.Person_dbo.Address_addressId] FOREIGN KEY ([addressId]) REFERENCES [dbo].[Address] ([addressId]) ON DELETE CASCADE
);


GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_personId]
    ON [dbo].[Person]([personId] ASC);


GO
CREATE NONCLUSTERED INDEX [IX_addressId]
    ON [dbo].[Person]([addressId] ASC);

