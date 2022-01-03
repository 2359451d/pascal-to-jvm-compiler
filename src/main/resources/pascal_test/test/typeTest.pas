(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 03/01/2022
 *)
{$mode iso}
{$RANGECHECKS on}
program typeTest;
type
  Arrayfoo            = array [0 .. 9] of Integer;  { array definition }
  { Def of a pointer to another type identifier }
  PInteger            = ^arrayfoo;

    (*Recordfoo           = record                      { record definition }
                      Bar: Integer;
                      end;*)

  { schema def with discriminants ``x, y: Integer'' }
  CharSetFoo          = set of Char;              { Def of a set }
  SubrangeFoo         = -123..456;                { subrange def }
  EnumeratedFoo       = (Pope,John,the,Second);   { enum type def }

  { Def of an alias name for another type identifier }
  IdentityFoo         = Integer;
var
  enumVar : EnumeratedFoo;
begin
  enumVar:= the;
{WriteLn(ord(enumVar));}
end.