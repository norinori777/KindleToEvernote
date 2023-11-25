INSERT INTO public.m_settings (name, value, created_at, updated_at, version)
VALUES ('setting1', 'value1', current_timestamp, current_timestamp, 1),
       ('setting2', 'value2', current_timestamp, current_timestamp, 1),
       ('setting3', 'value3', current_timestamp, current_timestamp, 1);


INSERT INTO public.m_output_pdf_paths (initial, path, created_at, updkated_at, version)
VALUES ('A', '/path/to/fileA', current_timestamp, current_timestamp, 1),
       ('B', '/path/to/fileB', current_timestamp, current_timestamp, 1),
       ('C', '/path/to/fileC', current_timestamp, current_timestamp, 1);
