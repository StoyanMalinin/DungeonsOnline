import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DummyTest {
    @Test
    void dummyTest() {
        List<Integer> l = mock(List.class);
        when(l.size()).thenReturn(5);

        assertEquals(5, l.size());
    }
}
