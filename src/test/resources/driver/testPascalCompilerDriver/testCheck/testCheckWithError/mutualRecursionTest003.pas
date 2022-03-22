(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 16/01/2022
 *)
{$mode iso}
{$RANGECHECKS on}
program mutualRecursionTest;

function S(n : Integer): Integer; forward; {Forward declaration cannot be resolved. Implementation is missing}

function F(n : Integer) : Integer;
begin
  if n = 0 then
    F := 1
  else
    F := n - S(F(n-1));
end;

var
  i : Integer;

begin
  for i := 0 to 19 do begin
    write(F(i) : 4)
  end;

  {writeln;}

  for i := 0 to 19 do begin
    write(S(i) : 4)
  end;
  {writeln;}
end.