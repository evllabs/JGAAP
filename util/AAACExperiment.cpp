#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main()
{
  string line;
  int j = 1;
  getline(cin, line);
  cout << line << endl;
  while(!cin.eof()) {
    j++;
    getline(cin, line);
    if(line.length() <= 1) continue; 
    for(char i = 'A'; i <= 'M'; i++) {
      cout << (100000*j + ((int)(i)-'A')) << "," << line << ",../docs/aaac/Demos/load" << i << ".csv" << endl;
    }
  }
}
