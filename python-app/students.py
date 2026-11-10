import psycopg2

def get_connection():
    return psycopg2.connect(
        host="localhost", database="tp", user="postgres", password="postgres"
    )

def add_student(conn, name, grade):
    with conn.cursor() as cur:
        cur.execute("INSERT INTO students (name, grade) VALUES (%s, %s) RETURNING id", (name, grade))
        student_id = cur.fetchone()[0]
    conn.commit()
    return student_id

def get_student(conn, student_id):
    with conn.cursor() as cur:
        cur.execute("SELECT id, name, grade FROM students WHERE id = %s", (student_id,))
        row = cur.fetchone()
    if row:
        return {"id": row[0], "name": row[1], "grade": float(row[2])}
    return None

def average_grade(conn):
    with conn.cursor() as cur:
        cur.execute("SELECT AVG(grade) FROM students")
        result = cur.fetchone()[0]
    if result is None:
        return None
    return float(result)
