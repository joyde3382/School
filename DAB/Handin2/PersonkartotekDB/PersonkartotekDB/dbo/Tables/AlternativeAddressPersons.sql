CREATE TABLE [dbo].[AlternativeAddressPersons] (
    [AlternativeAddress_AAId] INT NOT NULL,
    [Person_personId]         INT NOT NULL,
    CONSTRAINT [PK_dbo.AlternativeAddressPersons] PRIMARY KEY CLUSTERED ([AlternativeAddress_AAId] ASC, [Person_personId] ASC),
    CONSTRAINT [FK_dbo.AlternativeAddressPersons_dbo.AlternativeAddress_AlternativeAddress_AAId] FOREIGN KEY ([AlternativeAddress_AAId]) REFERENCES [dbo].[AlternativeAddress] ([AAId]) ON DELETE CASCADE,
    CONSTRAINT [FK_dbo.AlternativeAddressPersons_dbo.Person_Person_personId] FOREIGN KEY ([Person_personId]) REFERENCES [dbo].[Person] ([personId]) ON DELETE CASCADE
);


GO
CREATE NONCLUSTERED INDEX [IX_AlternativeAddress_AAId]
    ON [dbo].[AlternativeAddressPersons]([AlternativeAddress_AAId] ASC);


GO
CREATE NONCLUSTERED INDEX [IX_Person_personId]
    ON [dbo].[AlternativeAddressPersons]([Person_personId] ASC);

