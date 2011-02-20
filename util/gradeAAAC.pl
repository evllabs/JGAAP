#!/usr/bin/perl
use strict;

my %right;
my %total;

my $problem = "";
my $correct = "";

my $aaacScore = 0.0;
my $aaacRight = 0.0;
my $aaacTotal = 0.0;

print "Summary results:\n";

while(<>) {
    if(m%Correct: (\S+).*/problem([A-M])%) {
	$correct = $1;
	$problem = $2;
	$total{$problem} = $total{$problem} + 1.0;
	$aaacTotal = $aaacTotal + 1.0;
    }
    elsif(/^1\. (\S+) \d\.\d+/) {
	if($1 eq $correct) {
	    $right{$problem} = $right{$problem} + 1.0;
	    $aaacRight = $aaacRight + 1.0;
	}
    }
}

for $problem (keys %total) {
    print "Problem $problem : $right{$problem}/$total{$problem} (" . (100.0 * $right{$problem} / $total{$problem}) . "%)\n";
    $aaacScore = $aaacScore + (100.0 * $right{$problem} / $total{$problem});
}

print "Total Correct : $aaacRight / $aaacTotal (" . (100.0 * $aaacRight / $aaacTotal) . "%)\n";
print "AAAC Score: $aaacScore%\n";
