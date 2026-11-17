<?php
use PHPUnit\Framework\TestCase;
class StudentsTest extends TestCase {
    private $students;
    protected function setUp(): void {
        $this->students = new Students();
        $pdo = new PDO('pgsql:host=localhost;dbname=tp', 'postgres', 'postgres');
        $pdo->exec("TRUNCATE students RESTART IDENTITY");
    }
    public function testAddAndGet() {
        $id = $this->students->addStudent("Ali", 15); $s = $this->students->getStudent($id);
        $this->assertSame("Ali", $s['name']); $this->assertEquals(15.0, $s['grade']);
    }
    public function testGetMissing() { $this->assertNull($this->students->getStudent(9999)); }
    public function testAverage() {
        $this->students->addStudent("Amel", 10); $this->students->addStudent("Karim", 14);
        $this->assertEquals(12.0, $this->students->averageGrade());
    }
}
