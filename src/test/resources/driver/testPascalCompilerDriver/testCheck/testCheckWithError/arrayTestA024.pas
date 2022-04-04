{$mode iso}
{$RANGECHECKS ON}
program arrayTestA024;

type
  array1dim = array ['a'..'e'] of integer;
var
  a: array1dim;
  i: char;
  n: integer;
begin
  (* loop to show you can use chars to index arrays *)
  (* index validation can might be checked at the runtime *)
  (* while some compilers provide the possibility to be checked at the compile time*)
  for i := 'a' to 'e' do {this is okay}
  begin
    n := n + 5;
    a[i] := n;
  end;

  for i := 'a' to 'f' do {invalid indexing}
  begin
    n := n + 5;
    a[i] := n;
  end;
end.
