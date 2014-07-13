CREATE SCHEMA IF NOT EXISTS `sightseeing` ;
commit;

CREATE  TABLE IF NOT EXISTS `sightseeing`.`place` (
  `idsightseeing` INT NOT NULL ,
  `alias` VARCHAR(45) NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `notes` VARCHAR(2000) NULL ,
  PRIMARY KEY (`idsightseeing`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
  ENGINE = InnoDB;
commit;

CREATE  TABLE IF NOT EXISTS `sightseeing`.`addresses` (
  `idaddress` INT NOT NULL ,
  `sight_id` INT NULL ,
  `alias` VARCHAR(45) NOT NULL ,
  `address` VARCHAR(45) NULL ,
  `metro` VARCHAR(45) NULL ,
  `notes` VARCHAR(500) NULL ,
  PRIMARY KEY (`idaddress`) ,
  UNIQUE INDEX `unique_alias_UNIQUE` (`sight_id`, `alias` ASC) ,
  INDEX `place_fk` (`sight_id` ASC) ,
  CONSTRAINT `address_place_fk`
  FOREIGN KEY (`sight_id` )
  REFERENCES `sightseeing`.`place` (`idsightseeing` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;
commit;

CREATE  TABLE IF NOT EXISTS `sightseeing`.`tickets` (
  `idtikets` INT NOT NULL ,
  `sight_id` INT NULL ,
  `adult` VARCHAR(45) NULL ,
  `children` VARCHAR(45) NULL ,
  `notes` VARCHAR(200) NULL ,
  PRIMARY KEY (`idtikets`) ,
  INDEX `place_fk` (`sight_id` ASC) ,
  CONSTRAINT `tickets_place_fk`
  FOREIGN KEY (`sight_id` )
  REFERENCES `sightseeing`.`place` (`idsightseeing` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;
commit;

CREATE  TABLE IF NOT EXISTS `sightseeing`.`working_hours` (
  `idworking_hours` INT NOT NULL ,
  `sight_id` INT NULL ,
  `closed_days` VARCHAR(45) NULL ,
  `open_hours` VARCHAR(45) NULL ,
  `notes` VARCHAR(500) NULL ,
  PRIMARY KEY (`idworking_hours`) ,
  INDEX `place_fk` (`sight_id` ASC) ,
  CONSTRAINT `hours_place_fk`
  FOREIGN KEY (`sight_id` )
  REFERENCES `sightseeing`.`place` (`idsightseeing` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;
commit;