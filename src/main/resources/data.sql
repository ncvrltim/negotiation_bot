-- data.sql

INSERT INTO car (make, model, car_year, min_bid_amount, sold_out, terms) VALUES ('Toyota', 'Camry', 2022, 15000.0, false, ' My asking price is $17000. Sell if revised bid is higher than my asking price or within 5% of asking price. If the revised bid is below $16000 please provide alternative vehicles that may fit buyer''s budget else offer a free six-month warranty extension for a higher bid.');
INSERT INTO car (make, model, car_year, min_bid_amount, sold_out, terms) VALUES ('Honda', 'Accord', 2021, 13500.0, false, 'The car is listed at $16,000. The seller is open to negotiating within a reasonable range, but any offer below $16,000 will be politely declined. Additionally, the seller is willing to consider including an extended warranty if the offer is within $16,000 to $20,000. The car has a mileage of approximately 40,000 miles. ');
INSERT INTO car (make, model, car_year, min_bid_amount, sold_out, terms) VALUES ('Hyundai', 'Tucson', 2016, 16000.0, false, 'The car is listed at $18,000. The seller is open to negotiating within a reasonable range, but any offer below $17,000 will be politely declined. Additionally, the seller is willing to consider including an extended warranty if the offer is within $17,000 to $20,000. The car has a mileage of approximately 40,000 miles. ');

INSERT INTO car_user (name, email) VALUES ('John Doe', 'john@example.com');
INSERT INTO car_user (name, email) VALUES ('Jane Smith', 'jane@example.com');
INSERT INTO car_user (name, email) VALUES ('Sam Pett', 'sam@example.com');

INSERT INTO bid (car_car_id, user_id, bid_amount, max_bid_amount, status) VALUES (1, 1, 15500.0,16500.0, 'PENDING');
INSERT INTO bid (car_car_id, user_id, bid_amount, max_bid_amount, status) VALUES (2, 2, 16500.0,17500.0, 'PENDING');
