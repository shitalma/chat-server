import com.prateekj.InputScanner;
import com.prateekj.UserInputReader;
import com.prateekj.UserInputReaderObserver;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class UserInputReaderTest {

    UserInputReaderObserver observer = mock(UserInputReaderObserver.class);
    InputScanner scanner = mock(InputScanner.class);

    boolean firstCall = true;
    Answer<Boolean> return_true_only_firstTime = new Answer<Boolean>() {
        @Override
        public Boolean answer(InvocationOnMock invocation) throws Throwable {
            if(firstCall) {
                firstCall = false;
                return true;
            }
            return false;
        }
    };

    @Test
    public void readerInformsWhenUserQuits() {
        when(scanner.hasNext()).then(return_true_only_firstTime);
        when(scanner.nextLine()).thenReturn("quit");

        UserInputReader reader = new UserInputReader(scanner, observer);
        reader.start();

        verify(observer,times(1)).onQuit();
    }
    @Test
    public void readerDoesNotInformsWhenUserDoesTypesSomethingElseThanQuit() {
        when(scanner.hasNext()).thenReturn(true);
        when(scanner.nextLine()).thenReturn("any");

        UserInputReader reader = new UserInputReader(scanner, observer);
        reader.start();

        verify(observer,never()).onQuit();
    }
}
