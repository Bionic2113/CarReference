DELETE FROM cars WHERE EXISTS(SELECT 1 FROM cars LIMIT 1);
insert into cars(number, brand, model, color, year) values ('В035ЕЕ774', 'Tesla', 'Model X', 'white', 2017),
('А111АА777', 'Toyota', 'Land Cruiser Prado', 'black', 2013);
