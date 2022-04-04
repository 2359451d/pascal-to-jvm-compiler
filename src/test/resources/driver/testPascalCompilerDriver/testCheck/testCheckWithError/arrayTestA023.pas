program arrayTestA023;

var
  charArr : packed array [1..5] of char;
  charArr2 : array [1..5] of char;
begin
  WriteLn(charArr);

  {cannot write directly, except for the packed char[] i.e. strings}
  WriteLn(charArr2);
end.
