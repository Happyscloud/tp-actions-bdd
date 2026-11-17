const { getConnection, addStudent, getStudent, averageGrade } = require('./students');
let client;
beforeEach(async () => { client = await getConnection(); await client.query('TRUNCATE students RESTART IDENTITY'); });
afterEach(async () => { await client.end(); });
test('add and get', async () => {
    const id = await addStudent(client, 'Ali', 15);
    const s = await getStudent(client, id);
    expect(s.name).toBe('Ali'); expect(parseFloat(s.grade)).toBe(15.0);
});
test('get missing', async () => { expect(await getStudent(client, 9999)).toBeNull(); });
test('average', async () => {
    await addStudent(client, 'Amel', 10); await addStudent(client, 'Karim', 14);
    expect(await averageGrade(client)).toBe(12.0);
});
