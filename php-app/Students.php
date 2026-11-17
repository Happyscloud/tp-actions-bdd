<?php
class Students {
    private $pdo;
    public function __construct() { $this->pdo = new PDO('pgsql:host=localhost;dbname=tp', 'postgres', 'postgres'); }
    public function addStudent($name, $grade) {
        $stmt = $this->pdo->prepare("INSERT INTO students (name, grade) VALUES (?, ?) RETURNING id");
        $stmt->execute([$name, $grade]); return $stmt->fetchColumn();
    }
    public function getStudent($id) {
        $stmt = $this->pdo->prepare("SELECT name, grade FROM students WHERE id = ?");
        $stmt->execute([$id]); $row = $stmt->fetch(PDO::FETCH_ASSOC); return $row ? $row : null;
    }
    public function averageGrade() {
        $stmt = $this->pdo->query("SELECT AVG(grade) FROM students"); $avg = $stmt->fetchColumn();
        return $avg === null ? null : (float)$avg;
    }
}
