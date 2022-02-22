(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 22/02/2022
 *)
program if2Test;
var
  { local variable definition }
  a : integer;
begin
  a := 100;
  (* check the boolean condition *)
  if (a = 10)  then
    writeln('Value of a is 10' )
  else if ( a = 20 ) then
    writeln('Value of a is 20' )
  else if( a = 30 ) then
    writeln('Value of a is 30' )
  else
    writeln('None of the values is matching' );
  writeln('Exact value of a is: ', a );

  if (a=100) then
  begin
    if (a>10) then WriteLn('a!=10')
    else
    begin
      if (a<100) then WriteLn('a');
    end;
  end
  else Write('?');
end.