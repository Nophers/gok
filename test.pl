use strict;
use warnings;

sub new {
    my ($class, $rows, $cols) = @_;
    my $self = {
        rows => $rows,
        cols => $cols,
        grid => [],
        next => [],
    };
    bless $self, $class;
    return $self;
}

sub initialize {
    my ($self) = @_;

    for my $row (0 .. $self->{rows} - 1) {
        for my $col (0 .. $self->{cols} - 1) {
            $self->{grid}[$row][$col] = (rand() < 0.5);
        }
    }
}

sub update {
    my ($self) = @_;

    for my $row (0 .. $self->{rows} - 1) {
        for my $col (0 .. $self->{cols} - 1) {
            my $neighbors = $self->count_neighbors($row, $col);
            if ($self->{grid}[$row][$col] && $neighbors >= 2 && $neighbors <= 3) {
                $self->{next}[$row][$col] = 1;
            }
            elsif (!$self->{grid}[$row][$col] && $neighbors == 3) {
                $self->{next}[$row][$col] = 1;
            }
            else {
                $self->{next}[$row][$col] = 0;
            }
        }
    }

    for my $row (0 .. $self->{rows} - 1) {
        for my $col (0 .. $self->{cols} - 1) {
            $self->{grid}[$row][$col] = $self->{next}[$row][$col];
        }
    }
}

sub count_neighbors {
    my ($self, $row, $col) = @_;
    my $count = 0;
    for my $i (-1 .. 1) {
        for my $j (-1 .. 1) {
            next if $i == 0 && $j == 0;
            $count += $self->get($row + $i, $col + $j);
        }
    }
    return $count;
}

sub get {
    my ($self, $row, $col) = @_;
    my $new_row = ($row + $self->{rows}) % $self->{rows};
    my $new_col = ($col + $self->{cols}) % $self->{cols};
    return $self->{grid}[$new_row][$new_col];
}

sub run_simulation {
    my ($self) = @_;

    $self->initialize();

     while (1) {
        $self->update();
        print "\e[2J\e[H";
        for my $row (0 .. $self->{rows} - 1) {
            for my $col (0 .. $self->{cols} - 1) {
                print $self->{grid}[$row][$col] ? 'X' : ' ';
            }
            print "

";
        }
        sleep 1;
    }
}

run_simulation()