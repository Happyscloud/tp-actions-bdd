const { Client } = require('pg');
async function getConnection() {
    const client = new Client({ host: 'localhost', database: 'tp', user: 'postgres', password: 'postgres' });
    await client.connect(); return client;
}
async function addStudent(client, name, grade) {
    const res = await client.query('INSERT INTO students (name, grade) VALUES ($1, $2) RETURNING id', [name, grade]);
    return res.rows[0].id;
}
async function getStudent(client, id) {
    const res = await client.query('SELECT * FROM students WHERE id = $1', [id]);
    return res.rows.length === 0 ? null : res.rows[0];
}
async function averageGrade(client) {
    const res = await client.query('SELECT AVG(grade) FROM students');
    return res.rows[0].avg ? parseFloat(res.rows[0].avg) : null;
}
module.exports = { getConnection, addStudent, getStudent, averageGrade };
