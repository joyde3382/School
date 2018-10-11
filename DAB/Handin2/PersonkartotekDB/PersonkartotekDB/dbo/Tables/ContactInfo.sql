CREATE TABLE [dbo].[ContactInfo] (
    [contactId]       INT            IDENTITY (1, 1) NOT NULL,
    [phoneNumber]     INT            NOT NULL,
    [phoneType]       NVARCHAR (MAX) NULL,
    [phoneCompany]    NVARCHAR (MAX) NULL,
    [person_personId] INT            NOT NULL,
    CONSTRAINT [PK_dbo.ContactInfo] PRIMARY KEY CLUSTERED ([contactId] ASC),
    CONSTRAINT [FK_dbo.ContactInfo_dbo.Person_personId_personId] FOREIGN KEY ([person_personId]) REFERENCES [dbo].[Person] ([personId]) ON DELETE CASCADE
);


GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_contactId]
    ON [dbo].[ContactInfo]([contactId] ASC);


GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_phoneNumber]
    ON [dbo].[ContactInfo]([phoneNumber] ASC);


GO
CREATE NONCLUSTERED INDEX [IX_person_personId]
    ON [dbo].[ContactInfo]([person_personId] ASC);

