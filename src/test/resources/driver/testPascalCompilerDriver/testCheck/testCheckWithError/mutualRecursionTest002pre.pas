(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 16/01/2022
 *)
{$mode iso}
{$RANGECHECKS on}
program mutualRecursionTest002pre;

{M definition comes after F which uses it}
function S(n : Integer): Integer; forward;

function F(n : Integer) : Integer;
begin
    F := 1
end;

function S;
begin
  if n = 0 then
    S := 0
  else
    S := n - F(S(n-1));
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
end.
