(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 02/01/2022
 *)
{$mode iso}
{$rangeChecks on}
program enumTest007pre;
type
  cardsuit = (clubs, diamonds, hearts, spades);
var
  cardsuit2 : (clubs2, diamonds2, hearts2, spades2);
  boolVar: Boolean;
begin
  boolVar := clubs2 < diamonds2;
end.
