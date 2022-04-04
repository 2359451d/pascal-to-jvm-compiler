program relationalOperator001;
var
  a, b: integer;

begin
  a := 21;
  b := 10;

  if a = b then
    writeln('Line 1 - a is equal to b' )
  else
    writeln('Line 1 - a is not equal to b' );

  if  a < b then
    writeln('Line 2 - a is less than b' )
  else
    writeln('Line 2 - a is not less than b' );

  if  a > b then
    writeln('Line 3 - a is greater than b' )
  else
    writeln('Line 3 - a is greater than b' );

  (* Lets change value of a and b *)
  a := 5;
  b := 20;

  if  a <= b then
    writeln('Line 4 - a is either less than or equal to b' );

  if ( b >= a ) then
    writeln('Line 5 - b is either greater than  or equal to ' );
end.
