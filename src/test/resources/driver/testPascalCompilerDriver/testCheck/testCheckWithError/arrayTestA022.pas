program arrayTestA022;

type
  arrType = array [1..50] of Integer;
var
  x: array [0..100] of array [1..50] of Integer;
  arrVar: arrType;
  charArr : packed array [1..5] of char;
begin

  WriteLn(charArr);

  {cannot write directly, except for the packed char[] i.e. strings}
  arrVar := x[0];
  WriteLn(arrVar);
end.
