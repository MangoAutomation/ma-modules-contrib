CREATE TABLE exampleAccessCardSites
(
    accessCardId           INT NOT NULL,
    siteId                 INT NOT NULL
);
ALTER TABLE exampleAccessCardSites
    ADD CONSTRAINT exampleAccessCardSitesFk1 FOREIGN KEY (accessCardId) REFERENCES exampleAccessCards (id);
ALTER TABLE exampleAccessCardSites
    ADD CONSTRAINT exampleAccessCardSitesFk2 FOREIGN KEY (siteId) REFERENCES exampleSites (id);
ALTER TABLE exampleAccessCardSites ADD CONSTRAINT exampleAccessCardSitesUnq1 UNIQUE (accessCardId, siteId);

