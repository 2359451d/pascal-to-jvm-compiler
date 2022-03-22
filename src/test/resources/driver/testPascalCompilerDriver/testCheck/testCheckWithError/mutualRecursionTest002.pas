(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 16/01/2022
 *)
{$mode iso}
{$RANGECHECKS on}
program mutualRecursionTest;

{M definition comes after F which uses it}
function S(n : Integer): Integer; forward;

function F(n : Integer) : Integer;
begin
  if n = 0 then
    F := 1
  else
    F := n - S(F(n-1));  {M not define}
end;

function S;
begin
  if n = 0 then
    S := 0
  else
    S := n - F(S(n-1));
end;

{Invalid function declaration}
function M;
begin
  if n = 0 then
    M := 0
  else
    M := n - F(M(n-1));
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